from skiplist import *


class MultiSet:
    ''' this class represents multiset. '''
    
    def __init__(self):
        ''' (MultiSet) -> NoneType
        Initialize an empty multiset.
        '''
        self._container = SkipList()
    
    def __contains__(self, element):
        ''' (MultiSet, obj) -> bool
        Return True if element belongs to this multiset.
        '''
        result = self._container.total_occurrences(element) > 0
        return result
        
    def count(self, element):
        ''' (MultiSet, obj) -> int
        Return number of occurrences of element in this multiset.
        '''
        result = self._container.total_occurrences(element)
        return result
        
    def insert(self, element):
        ''' (MultiSet, obj) -> NoneType
        Add this element into this multiset.
        '''
        self._container.insert(element)
        
    def remove(self, element):
        ''' (MultiSet, obj) -> NoneType
        Mutate this multiset, remove one occurrence of this element. Do nothing
        if the multiset does not contain element.
        '''
        try:
            self._container.remove(element)
        except:
            pass
        
    def clear(self):
        ''' (MultiSet) -> NoneType
        Remove all elements in this multiset.
        '''
        self._container = SkipList()

    def __len__(self):
        ''' (MultiSet) -> int
        Return number of elements in this multiset. Each copy of an element is
        counted seperately.
        '''
        result = len(self._container)
        return result
        
    def __repr__(self):
        ''' (MultiSet) -> str
        Return a string representation of this multiset of the form
        MultiSet([first-element, second_element, ... , last element])
        Each copy of an element is showned seperately. Order of elements are
        not guaranteed.
        '''
        if len(self) == 0:
            result = "MultiSet([])"
        # assume this multiset is not empty
        else:
            result = "MultiSet(["
            first_element = True
            for element in self:
                if isinstance(element, str):
                    result += "'" + str(element) + "'" + ", "
                else:
                    result += str(element) + ", "
            result = result[:-2]
            result += "])"
        return result
        
    def __eq__(self, other): # inc
        ''' (MultiSet, MultiSet) -> bool
        Return True if both multisets contain exactly the same element and
        same occurences of each element.
        '''
        # two multisets are equal only if their length are the same
        if len(self) == len(other):
            other_copy = other.copy()
            for element in self:
                other_copy.remove(element)
            result = len(other_copy) == 0
        else:
            result = False
        return result
            
    def __le__(self, other):
        ''' (MultiSet, MultiSet) -> bool
        Return True if all elements in this multiset are contained in the other
        multiset.
        '''
        result = False
        if len(self) <= len(other):
            other_copy = other.copy()
            for element in self:
                other_copy.remove(element)
            result = (len(other) - len(self)) == len(other_copy)
        return result
        
    def __sub__(self, other):
        ''' (MultiSet, MultiSet) -> MultiSet
        Return a new multiset contains difference between two given multisets.
        '''
        result = self.copy()
        for element in other:
            result.remove(element)
        return result
        
    def __isub__(self, other):
        ''' (MultiSet, MultiSet) -> NoneType
        Mutate this multiset, so all elements in other multiset are removed
        from this multiset.
        '''        
        for element in other:
            self.remove(element)
        return self
        
    def __add__(self, other):
        ''' (MultiSet, MultiSet) -> MultiSet
        Return a new multiset contains all elements from both input multisets.
        '''
        result = self.copy()
        for element in other:
            result.insert(element)
        return result
        
    def __iadd__(self, other):
        ''' (MultiSet, MultiSet) -> NoneType
        Mutate this multiset, so it contains all elements from both multisets.
        '''
        self = self + other
        return self
    
    def __and__(self, other):
        ''' (MultiSet, MultiSet) -> MultiSet
        Return a new multiset whicin contains all elements both multisets have
        in common.
        '''
        other_copy = other.copy()
        # create an empty result ready to use
        result = MultiSet()
        for element in self:
            if element in other_copy:
                other_copy.remove(element)
                result.insert(element)
        return result
            
    def __iand__(self, other):
        ''' (MultiSet, MultiSet) -> NoneType
        Mutate this multiset, so it contains all elements both multisets have
        in common.
        '''
        self = self.__and__(other)
        return self
        
    def isdisjoint(self, other):
        ''' (MultiSet, MultiSet) -> bool
        Return True if both multisets have nothing in common.
        '''
        result = len(self & other) == 0
        return result
        
    def __iter__(self):
        ''' (MultiSet) -> obj
        Return one element at a time in this multiset.
        '''
        for node in self._container:
            data = node.get_data()
            yield data
            
    def copy(self):
        ''' (MultiSet) -> MultiSet
        Return a shallow copy of this multiset.
        '''
        result = MultiSet()
        for element in self:
            result.insert(element)
        return result
    
        
if __name__ == "__main__":
    
    
    
    a = MultiSet()
    b = MultiSet()

    a.insert(1)
    a.insert(2)
    b.insert(1)
    
    print(a == b)