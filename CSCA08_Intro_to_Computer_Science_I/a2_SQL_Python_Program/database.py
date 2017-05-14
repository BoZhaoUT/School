EQUALITY = '='
GREATER_THAN = '>'


class Table():
    '''A class to represent a SQuEaL table'''

    def __init__(self):
        '''(Table) -> NoneType

        Initialize a Table object.
        '''
        self._dict = {}

    def set_dict(self, new_dict):
        '''(Table, dict of {str: list of str}) -> NoneType

        Populate this table with the data in new_dict.
        The input dictionary must be of the form:
            column_name: list_of_values
        '''
        self._dict = new_dict

    def get_dict(self):
        '''(Table) -> dict of {str: list of str}

        Return the dictionary representation of this table. The dictionary keys
        will be the column names, and the list will contain the values
        for that column.
        '''
        return self._dict

    def get_num_columns(self):
        '''(Table) -> int

        Return the number of columns of this table.
        '''
        return len(self._dict)

    def add_column_titles(self, column_titles):
        '''(Table, list of str) -> NoneType

        Add each element in column_titles as a column title into this table.
        REQ: all column titles must be unique.
        '''
        for next_column_title in column_titles:
            self._dict[next_column_title] = []

    def parse_csv(self, csv_file_handle):
        '''(Table, io.TextIOWrapper) -> NoneType

        Add content of csv_file_handle into this table.
        REQ: csv_file_handle must be properly formatted
        '''
        # read and add column titles into this table
        title_line = csv_file_handle.readline()
        title_line = Table._split_and_clean_a_line(title_line)
        self.add_column_titles(title_line)
        # read and add rows into this table
        for next_line in csv_file_handle:
            # skip if it is a blank line
            if next_line != '\n':
                row_line = Table._split_and_clean_a_line(next_line)
                self.add_row(title_line, row_line)

    def _split_and_clean_a_line(a_line):
        '''(str) -> [str or number]

        Given a csv formatted line. Return a list of entries
        with trailing and leading whitespaces removed.
        '''
        # create an empty result ready for use
        result = []
        # split and remove all trailing and leading whitespaces
        # of each element
        line_content = a_line.split(',')
        # loop through each element in this list
        for next_element in line_content:
            # remove leading and trailing whitespaces
            next_element = next_element.strip()
            # convert it into numerical value if possible
            next_element = Table._is_float(next_element)
            result.append(next_element)
        return result

    def add_row(self, title_line, row):
        '''(Table, list of str, str or list of str) -> NoneType

        Given a list of titles and a list of row entries. Add each entry
        under each title accordingly.
        REQ: each title in title_line must exist in this Table
        REQ: length of title_line == length of row
        REQ: if row is of type str, then row must be csv formatted
             all rows have the same number of values
        '''
        # if row is str type, then convert it into a list of str
        if isinstance(row, str):  # polymorphism
            row = Table._split_and_clean_a_line(row)
        # loop through both lists and add each element under
        # its column accordingly
        for i in range(len(title_line)):
            self._dict[title_line[i]].append(row[i])

    def copy(self):
        '''(Table) -> Table

        Return a shallow copy of this Table.
        '''
        # create a new table and copy the content of self into new table
        result = Table()
        column_titles = self.get_column_titles()
        result.add_column_titles(column_titles)
        for next_row in self:
            # make sure self and result table do not affect each other
            new_row = next_row[:]
            result.add_row(column_titles, next_row)
        return result

    def get_column_by_column_title(self, column_title):
        '''(Table) -> list of str

        Return content of a column.
        '''
        return self._dict[column_title]

    def get_column_titles(self):
        '''(Table) -> list of str

        Return column titles sorted.
        '''
        result = list(self._dict.keys())
        result.sort()
        return result

    def get_entry(self, column_title, index):
        '''(Table, list of str, int) -> str

        Return an entry based on given column_title and index.
        '''
        return (self._dict[column_title])[index]

    def __iter__(self):
        '''(Table) -> list of str

        Return one row at a time.
        '''
        # loop through each row in this table
        for i in range(len(self)):
            yield self[i]

    def __getitem__(self, index):
        '''(Table) -> list of str
        Return the i-th row in this table.

        REQ: i <= number of rows of this table.
        '''
        result = []
        # loop through all columns in this Table
        for next_column_title in self.get_column_titles():
            entry = self.get_entry(next_column_title, index)
            result.append(entry)
        return result

    def __mul__(self, other_table):
        '''(Table, Table) -> Table

        Return cartesian product of this table and other table.
        '''
        result = Table()
        # add column titles into result Table
        self_table_columns = self.get_column_titles()
        other_table_columns = other_table.get_column_titles()
        result.add_column_titles(self_table_columns)
        result.add_column_titles(other_table_columns)
        # obtain the column titles of result Table
        column_titles = self_table_columns + other_table_columns
        # loop through rows self and other Table
        for row_in_self in self:
            for row_in_other in other_table:
                new_row = row_in_self + row_in_other
                result.add_row(column_titles, new_row)
        return result

    def select_columns(self, column_titles):
        '''(Table, list of str) -> NoneType

        Delete the columns that are not in column_titles. Do nothing if
        column_titles has only one element and the element is '*'.
        '''
        # do nothing if ['*'] is given
        if column_titles[0] != '*':
            # loop through all column titles in this Table
            for next_column_title in self.get_column_titles():
                # delete unwanted columns
                if next_column_title not in column_titles:
                    del self._dict[next_column_title]

    def process_constraint(self, constraint):
        '''(Table, list of str) -> Table

        Return a table with rows that do not meet constraint in constraints
        deleted.
        REQ: The constraint must be of the form:
            [column_title, operator, column_title or value]
        REQ: operator is either '=' or '>'
        REQ: column_title(s) must exist
        '''
        # split given constraint based on the operator
        if EQUALITY in constraint:
            parsed_constraint = constraint.split(EQUALITY) + [EQUALITY]
        # assume the operator is GREATER_THAN
        else:
            parsed_constraint = constraint.split(GREATER_THAN) + [GREATER_THAN]
        # compare a column and a hard-coded value
        if "'" in parsed_constraint[1]:
            result = self.constraint_bettwen_column_and_value(parsed_constraint)
        else:
            result = self.constraint_between_two_columns(parsed_constraint)
        return result

    def constraint_bettwen_column_and_value(self, constraint):
        '''(Table, list of str) -> Table

        Return a table object wich only rows satisfy given constraint.
        REQ: The constraint must be of the form:
            [column_title, operator, value]
        REQ: column title must exist
        '''
        (column_title, hard_coded_value, operator) = constraint
        hard_coded_value = hard_coded_value.strip("'")
        # convert given value to a number if applicabl
        hard_coded_value = Table._is_float(hard_coded_value)
        # create an empty result table ready for use
        result = Table()
        column_titles = self.get_column_titles()
        result.add_column_titles(column_titles)
        column = self.get_column_by_column_title(column_title)
        # loop through each entry in this column
        for i in range(len(self)):
            if operator == EQUALITY:
                if Table._is_float(column[i]) == hard_coded_value:
                    result.add_row(column_titles, self[i])
            # assume the operator is GREATER_THAN
            else:
                if Table._is_float(column[i]) > hard_coded_value:
                    result.add_row(column_titles, self[i])
        return result

    def constraint_between_two_columns(self, constraint):
        '''(Table, list of str) -> Table

        Return a table object wich only rows satisfy given constraint.
        REQ: The constraint must be of the form:
            [column_title, column_title, operator]
        REQ: column titles must exist
        REQ: operator is in {'=', '>'}
        '''
        (left_column_title, right_column_title, operator) = constraint
        # create an empty result table ready for use
        result = Table()
        # get two columns
        left_column = self.get_column_by_column_title(left_column_title)
        right_column = self.get_column_by_column_title(right_column_title)
        # add column titles from two given tables into result table
        column_titles = self.get_column_titles()
        result.add_column_titles(column_titles)
        # loop through both columns
        for i in range(len(self)):
            if operator == EQUALITY:
                if left_column[i] == right_column[i]:
                    result.add_row(column_titles, self[i])
            # assume the operator is GREATER_THAN
            else:
                if left_column[i] > right_column[i]:
                    result.add_row(column_titles, self[i])
        return result

    def _is_float(value):
        '''(str) -> str or float

        Convert given value to a number if applicable.
        '''
        # attempt converting value to a float
        try:
            result = float(value)
        except:
            # do nothign if value cannot be converted into float
            result = value
        return result

    def print_csv(self):
        '''(Table) -> NoneType

        Print a representation of table in csv format.
        '''
        print(self.__repr__())

    def __repr__(self):
        '''(Table) -> str

        Return a string representation of this table.
        '''

        # no need to edit this one, but you may find it useful (you're welcome)
        dict_rep = self.get_dict()
        columns = list(dict_rep.keys())
        result = ','.join(columns)
        rows = len(self)
        # loop through all rows
        for i in range(rows):
            cur_column = []
            # loop through all columns
            for column in columns:
                cur_column.append(str(dict_rep[column][i]))
            result += '\n' + ','.join(cur_column)
        return result

    def __len__(self):
        '''(Table) -> int

        Return the legnth of this Table. Length represents the number fo rows
        in this table (title row does not count as a row).
        '''
        # if this table is empty
        if len(self._dict) == 0:
            result = 0
        # assume this table is not empty
        else:
            result = len(self._dict[list(self._dict.keys())[0]])
        return result

    def delete_row(self, index):  # unused
        '''(Table, int) -> NoneType

        Delete a row at index.
        REQ: index < number of rows in this table.
        '''
        for next_value in self._dict.values():
            del next_value[index]

    def delete_rows(self, indices):  # unused
        '''(Table, list of int) -> NoneType

        Delete rows at indices.
        REQ: each index in indices < number of rows in this table.
        '''
        for next_index in indices:
            self.delete_row(next_index)

    def __eq__(self, other_table):  # unused
        '''(Table, Table) -> bool

        Return True if two tables are equal. False otherwise.
        Two tables are equal if and only if two tables have exact columns and
        rows. The orders of titles and rows do not matter.
        '''
        result = True
        # compare if two tables have the same number of rows
        if len(self) != len(other):
            result = False
        # compare if two tables have the same number of columns
        elif self.get_num_columns() != other.get_num_columns():
            result = False
        # compare if two tables have the same columns and rows
        else:
            first_table_titles = self.get_column_titles()
            second_table_titles = other.get_column_titles()
            # compare if two tables have same column titles
            if first_table_titles != second_table_titles:
                result = False
            # compare if two tables have same rows
            else:
                pass
        return result


class Database():
    '''A class to represent a SQuEaL database'''

    def __init__(self):
        '''(Database) -> NoneType

        Initialize a database object.
        '''
        self._dict = {}

    def add_table(self, table_name, table_object):
        '''(Database, str, Table) -> NoneType

        Add table_object with its name into this database.
        '''
        self._dict[table_name] = table_object

    def get_table(self, table_name):
        '''(Database, str) -> Table

        Return a Table object with table_name.
        '''
        result = self._dict[table_name]
        return result

    def set_dict(self, new_dict):
        '''(Database, dict of {str: Table}) -> NoneType

        Populate this database with the data in new_dict.
        new_dict must have the format:
            table_name: table
        '''
        self._dict = new_dict

    def get_dict(self):
        '''(Database) -> dict of {str: Table}

        Return the dictionary representation of this database.
        The database keys will be the name of the table, and the value
        with be the table itself.
        '''
        return self._dict

    def __repr__(self):
        '''(Database) -> str

        Return all tables' name in this database.
        '''
        result = "This database has following table(s): "
        table_names = list(self._dict.keys())
        table_names.sort()
        result += ', '.join(table_names)
        return result
