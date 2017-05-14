# Functions for reading tables and databases

import glob
from database import *


# Write the read_table and read_database functions below

def read_table(file_name):
    '''(str) -> Table
    Return a table object. Given a name of csv formatted file.
    '''
    # create an empty result table ready for use
    result = Table()
    filehandle = open(file_name, 'r')
    # add content of this csv filehandle into result table
    result.parse_csv(filehandle)
    filehandle.close()
    return result


def read_database():
    '''
    () -> Database
    '''
    file_name_list = glob.glob('*.csv')
    # create an empty database ready for use
    result = Database()
    # loop through file_name_list and construct table objects accordingly
    for next_file_name in file_name_list:
        # construct a table name and a new table object
        table_name = next_file_name.split('.')[0]
        new_table = read_table(next_file_name)
        # add the table into database
        result.add_table(table_name, new_table)
    return result


if __name__ == '__main__':
    # test read_table
    result = read_table('books.csv')
    result.print_csv()

    # test read_database
    database_result = read_database()
    print(database_result)

