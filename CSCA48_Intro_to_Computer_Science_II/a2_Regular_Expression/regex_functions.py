"""
# Copyright 2013 Nick Cheng, Brian Harrington, Danny Heap, 2013, 2014
# Distributed under the terms of the GNU General Public License.
#
# This file is part of Assignment 1, CSC148, Winter 2014
#
# This is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This file is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this file.  If not, see <http://www.gnu.org/licenses/>.
"""

# Do not change this import statement, or add any of your own!
from regextree import RegexTree, StarTree, DotTree, BarTree

# Do not change anything above this comment

# Student code below this comment.

AND = '.'
OR = '|'
STAR = '*'
LEFT = '('
RIGHT = ')'
EPSILON = 'e'
ZERO = '0'
ONE = '1'
TWO = '2'
BINARY_OPERATORS = {AND, OR}
BASIC_SYMBOLS = {ZERO, ONE, TWO, EPSILON}

class InvalidRegexException(Exception):
    ''' An exception for invalid regex '''

class Stack:
    ''' A special stack for validating regular expressions. '''

    _PLACEHOLDER = ZERO

    def __init__(self):
        ''' (Stack) -> NoneType
        Create a new stack.
        '''
        self._content = []

    def is_empty(self):
        ''' (Stack) -> bool
        Return True iff this stack is empty.
        '''
        return self._content == []

    def push(self, element):
        ''' (Stack, obj) -> NoneType
        Push an item into this stack.
        '''
        self._content.append(element)

    def pop(self):
        ''' (Stack) -> obj
        Pop an element from this stack.
        Raise InvalidRegexException if this stack is empty
        '''
        if self.is_empty():
            raise InvalidRegexException
        return self._content.pop()

    def remove_left(self):
        ''' (Stack) -> NoneType
        Remove a LEFT symbol in this stack.
        Raise InvalidRegexException if the last item is not LEFT or
        this stack is empty
        '''
        result = self.pop()
        if result != LEFT:
            raise InvalidRegexException

    def pop_operator(self):
        ''' (Stack) -> str
        Remove and return an operator in this stack.
        Raise InvalidRegexException if the last element is not an
        operator or this stack is empty.
        '''
        result = self.pop()
        if result not in BINARY_OPERATORS:
            raise InvalidRegexException
        return result

    def pop_basic_symbol(self):
        ''' (Stack) -> str
        Remove and return a symbol in this stack.
        '''
        result = self.pop()
        if result not in BASIC_SYMBOLS:
            raise InvalidRegexException

    def pop_stars(self):
        ''' (Stack) -> str
        Pop stars in this stack untill a non-star symbol.
        Do nothing if the last item is not a star.
        Raise InvalidRegexException if this stack is empty.
        '''
        # remove trailing stars in this stack
        result = self.pop()
        while result == STAR:
            result = self.pop()
        # push back non-star symbol
        self.push(result)

    def remove_regex(self):
        ''' (Stack) -> NoneType
        Remove and return a regex with a binary operator in this stack.
        '''
        # a valid regex
        self.pop_stars()
        self.pop_basic_symbol()
        self.pop_operator()
        self.pop_stars()
        self.pop_basic_symbol()
        self.remove_left()
        self.push(self._PLACEHOLDER)

    def remove_simple_regex(self):
        ''' (Stack) ->
        '''
        self.pop_stars()
        self.pop_basic_symbol()
        if not self.is_empty():
            raise InvalidRegexException

def is_regex(my_str):
    ''' (str) -> bool
    Return True iff my_str is a valid regular expression.
    >>> is_regex('')
    False
    >>> is_regex('0')
    True
    >>> is_regex('()')
    False
    >>> is_regex('(0****.2*)*')
    True
    >>> is_regex('((((1.2*).1)|e).(1.2))')
    True
    >>> is_regex('((((1.2*).1)e).(1.2))')
    False
    >>> is_regex('(((((1.2*).1)|e).(1.2)))')
    False
    >>> is_regex('1**********')
    True
    >>> is_regex('*1')
    False
    '''
    stack = Stack()
    result = True
    try:
        # loop through given string
        for next_char in my_str:
            # push basic symbols (except RIGHT) into this stack
            if next_char in {ZERO, ONE, TWO, EPSILON, AND, OR, STAR, LEFT}:
                stack.push(next_char)
            # evaluate the regex or a sub-regex
            elif next_char == RIGHT:
                stack.remove_regex()
            # an unrecognized symbol
            else:
                raise InvalidRegexException
        stack.remove_simple_regex()
    except InvalidRegexException:
        result = False
    print(stack._content)
    return result


if __name__ == '__main__':
    import doctest
    doctest.testmod()