from reading import *
from database import *

# Below, write:
# *The cartesian_product function
# *All other functions and helper functions
# *Main code that obtains queries from the keyboard,
#  processes them, and uses the below function to output csv results


def cartesian_product(first_table, second_table):
    '''(Table, Table) -> Table
    Return a table object which is combined from 2 given tables.
    '''
    result = first_table * second_table
    return result


def run_query(database, query):
    '''(Database, str) -> Table
    Return a result Table based on query.
    >>> database = read_database()
    >>> run_query(database, "select * from games where g.genre='RPG'")
    g.title,g.genre,g.year
    Pokemon,RPG,1999.0
    Chrono_Trigger,RPG,1995.0
    Final_Fantasy,RPG,1990.0
    '''
    # parse given query into (column_titles, table_names, constraints)
    # constraints are optional
    parsed_query = parse_query(query)
    column_titles = parsed_query[0]
    table_names = parsed_query[1]
    # construct a raw table to work with
    for i in range(len(table_names)):
        if i == 0:
            result_table = database.get_table(table_names[i])
            # avoid mutating given database
            result_table = result_table.copy()
        else:
            current_table = database.get_table(table_names[i])
            result_table = cartesian_product(result_table, current_table)
    # process constraints(if any)
    if len(parsed_query) > 2:
        constraints = parsed_query[2]
        # loop through constraints and apply each of them on result table
        for constraint in constraints:
            result_table = result_table.process_constraint(constraint)
    # only keep selected columns
    result_table.select_columns(column_titles)
    return result_table


def parse_query(query):
    '''(str) -> tuple of list of str
    Given a valid query. Return a tuple of (column titles, table names) if no
    constraints given after where clause. Return a tuple of
    (column titles, table names, constraints) if there exists at least one
    constraint.
    '''
    query = query.split(' ', 5)
    table_names = query[3].split(',')
    column_titles = query[1].split(',')
    result = (column_titles, table_names)
    # if constraints present
    if len(query) > 4:
        constraints = query[5].split(',')
        result = (column_titles, table_names, constraints)
    return result


if (__name__ == "__main__"):
    database = read_database()
    exit = False
    # keep prompting user to enter a query
    while not exit:
        query = input("Enter a SQuEaL query, or a blank line to exit:")
        # user enter an empty line to exit
        if query == '':
            exit = True
        # process user's query
        else:
            result_table = run_query(database, query)
            result_table.print_csv()
