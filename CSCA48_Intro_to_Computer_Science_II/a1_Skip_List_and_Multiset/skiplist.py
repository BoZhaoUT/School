import random


class Node:
    ''' This class represents a parent class of all nodes '''

    def __init__(self):
        ''' (Node) -> NoneType
        Initialize a general node.
        '''
        self._down_node = None
        self._next_node = None
        self._value = None

    def get_next(self):
        ''' (Node) -> Node
        Return this node's next node.
        '''
        result = self._next_node
        return result

    def has_next(self):
        ''' (Node) -> bool
        Return True if this node's next node is not None. False otherwise.
        '''
        result = self._next_node is not None
        return result

    def set_next(self, new_next_node):
        ''' (Node, Node) -> NoneType
        Set this node's next node to next_node.
        '''
        self._next_node = new_next_node

    def get_down(self):
        ''' (Node) -> Node
        Return this node's down node.
        '''
        result = self._down_node
        return result

    def has_down(self):
        ''' (Node) -> bool
        Return True if this node's down node is not None. False otherwise.
        '''
        result = self._down_node is not None
        return result

    def set_down(self, new_down_node):
        ''' (Node, Node) -> NoneType
        Set this node's next node to new_down_node.
        '''
        self._down_node = new_down_node

    def get_data(self):
        ''' (Node) -> obj
        Return the data stored at this node.
        '''
        result = self._data
        return result

    def __lt__(self, other):
        ''' (Node, Node) -> bool
        Return True if this node is less than or equal to other node. False
        otherwise. If other node is a TailNode, then return True. i.e.
        all nodes are less than or equal to a tail node. If this node
        is a HeadNode, then return True. i.e. all head nodes are less are less
        than other nodes.
        '''
        if isinstance(other, TailNode) or isinstance(self, HeadNode):
            result = True
        elif isinstance(self, TailNode):
            result = False
        else:
            result = self._data < other.get_data()
        return result

    def __str__(self):
        ''' (HeadNode) -> str
        Return a string representation of this node.
        '''
        result = str(self._data)
        return result

    def __repr__(self):
        ''' (HeadNode) -> str
        Return a string representation of this node.
        '''
        return self.__str__()


class HeadNode(Node):
    ''' This class represents a head node. '''

    def __init__(self, next_node=None):
        ''' (HeadNode) -> NoneType
        Initialize a head node.
        '''
        self._down_node = None
        self._next_node = next_node
        # string representation of a head node
        self._data = "H"


class TailNode(Node):
    ''' This class represents a tail node. '''

    def __init__(self):
        ''' (TailNode) -> NoneType
        Initialize a tail node.
        '''
        self._down_node = None
        self._next_node = None
        # string representation of a tail node
        self._data = "T"


class ElementNode(Node):
    ''' This class represents an element node. '''

    def __init__(self, data):
        ''' (ElementNode, obj) -> NoneType
        Initialize a new element node with given data.
        '''
        self._next_node = None
        self._down_node = None
        self._data = data
        
    def __eq__(self, other):
        ''' (ElementNode, ElementNode) -> bool
        Return True if both nodes have the same data.
        '''
        result = self._data == other.get_data()
        return result


class SkipList:
    ''' This class represents a skiplist. '''

    def __init__(self):
        ''' (SkipList) -> NoneType
        Initialize a new empty skiplist.
        '''
        # by default, a skiplist has 1 level at creation
        self._level = 1
        self._head = HeadNode()
        self._tail = TailNode()
        self._head.set_next(self._tail)

    def __str__(self):
        ''' (SkipList) -> str
        Return a string representation of this skiplist.
        '''
        result = ''
        current_node = self._head
        # store head node on current level
        head_node = self._head
        # loop through all levels
        for num_level in range(self._level):
            # add string representation of nodes on current level
            result += str(current_node)
            while current_node.has_next():
                result += " -> "
                current_node = current_node.get_next()
                result += str(current_node)
            result += "\n"
            head_node = head_node.get_down()
            current_node = head_node
        return result

    def __repr__(self):
        ''' (SkipList) -> str
        Return a string representation of this skiplist.
        '''
        return self.__str__()

    def search(self, data, initial_node=None):
        ''' (SkipList, obj) -> Node
        Return the node pointing to a node containig data. Return a TailNode
        if this skiplist does not contain data.
        '''
        search_node = ElementNode(data)
        # initialize a pointer
        if not initial_node:
            initial_node = self._head
        current_node = initial_node
        keep_searching = True
        while keep_searching:
            next_node = current_node.get_next()
            # found result
            if next_node == search_node:
                result = current_node
                keep_searching = False
            # move horizontally
            elif next_node < search_node:
                current_node = current_node.get_next()
            # move vertically
            elif current_node.has_down():
                current_node = current_node.get_down()
            else:
                # assume current_node is on the lowest level. i.e. it has no
                # down node
                result = current_node
                keep_searching = False
        return result

    def remove(self, data):
        ''' (SkipList, obj) -> NoneType
        Return the node containing data and all its vertical copies. Do nothing
        if this skiplist does not contain data.
        '''
        # initial pointers
        prev_node = self.search(data)
        current_node = prev_node.get_next()
        # in case this skiplist contains the data
        if current_node.get_data() == data:
            prev_node.set_next(prev_node.get_next().get_next())
            # there are some nodes haven't removed
            while prev_node.has_down():
                prev_node = self.search(data, prev_node.get_down())
                prev_node.set_next(prev_node.get_next().get_next())

    def insert(self, data):
        ''' (SkipList, obj) -> NoneType
        Add a node containing data into this skiplist.
        '''
        new_node_level = SkipList._determine_node_level()
        # in case there will be several new levels
        if new_node_level > self._level:
            new_head = HeadNode(self._tail)
            self._insert_empty_level(new_node_level, new_head)
            self._level = new_node_level
            self._head = new_head
            initial_node = new_head
        elif new_node_level == self._level:
            initial_node = self._head
        else:
            # assume new_node_level < self._level
            initial_node = self._head
            for num_level in range(self._level - new_node_level):
                initial_node = initial_node.get_down()
        # recursively add new nodes
        self._insert_helper(data, initial_node)
            
    def _insert_empty_level(self, current_level, upper_level_head):
        ''' (SkipList, HeadNode) -> NoneType
        Add several empty levels into this skiplist.
        '''
        if current_level > self._level + 1:
            new_head_on_lower_level = HeadNode(self._tail)
            upper_level_head.set_down(new_head_on_lower_level)
            self._insert_empty_level(current_level - 1, new_head_on_lower_level)
        else:
            # assume current level is one level above self._head
            upper_level_head.set_down(self._head)
        
    def _insert_helper(self, data, initial_node, upper_level_new_node=None):
        ''' (SkipList, obj) -> NoneType
        Add a node containing data into each level.
        '''
        # initialize two pointers
        current_node = initial_node
        next_node = current_node.get_next()
        new_node = ElementNode(data)
        while next_node < new_node:
            # move horizontally
            current_node = next_node
            next_node = next_node.get_next()
        # update horizontal links
        current_node.set_next(new_node)
        new_node.set_next(next_node)
        # update vertical link
        if upper_level_new_node:
            upper_level_new_node.set_down(new_node)
        # recursively add new nodes on lower levels
        if current_node.has_down():
            self._insert_helper(data, current_node.get_down(), new_node)

    def _determine_node_level(p_value=0.5):
        ''' (float) -> int
        Return a whole number represents number of levels this node will have.
        REQ: 0 < p < 1
        '''
        random_num = random.random()
        # by default, a node must have at least one level
        result = 1
        while random_num < p_value:
            result += 1
            random_num = random.random()
        return result
    
    def total_occurrences(self, data):
        ''' (SkipList, obj) -> int
        Return the number of occurrences of data. Vertical duplications are
        ignored.
        '''
        # initialize a pointer
        prev_node = self.search(data)
        data_node = ElementNode(data)
        result = 0
        next_node = prev_node.get_next()
        # check if there is a node containing input data in this skiplist
        if next_node == data_node:
            while prev_node.has_down():
                prev_node = self.search(data, prev_node.get_down())
            # assume it's bottom level
            while prev_node.get_next() == data_node:
                result += 1
                prev_node = prev_node.get_next()
        return result
    
    def __len__(self):
        ''' (SkipList, obj) -> int
        Return the number of nodes on the bottom level.
        '''
        result = 0
        for node in self:
            result += 1
        return result
    
    def __iter__(self):
        ''' (SkipList) -> ElementNode
        Return one element node at a time.
        '''
        current_node = self._head
        down_node = current_node.get_down()
        # move vertically
        while down_node:
            current_node = down_node
            down_node = current_node.get_down()
        # move horizontally
        next_node = current_node.get_next()
        while not isinstance(next_node, TailNode):
            current_node = next_node
            yield next_node
            next_node = current_node.get_next()
            
            
        
            


if __name__ == "__main__":
    '''
    # level 4
    my_list = SkipList()

    # level 3
    Three_Head = HeadNode()
    Three_7 = ElementNode(7)
    Three_17 = ElementNode(17)
    Three_Tail = TailNode()

    Three_Head.set_next(Three_7)
    Three_7.set_next(Three_17)
    Three_17.set_next(Three_Tail)

    my_list._head.set_down(Three_Head)
    my_list._tail.set_down(Three_Tail)

    # level 2
    Two_Head = HeadNode()
    Two_3 = ElementNode(3)
    Two_7 = ElementNode(7)
    Two_11 = ElementNode(11)
    Two_17 = ElementNode(17)
    Two_Tail = TailNode()

    Two_Head.set_next(Two_3)
    Two_3.set_next(Two_7)
    Two_7.set_next(Two_11)
    Two_11.set_next(Two_17)
    Two_17.set_next(Two_Tail)

    Three_Head.set_down(Two_Head)
    Three_7.set_down(Two_7)
    Three_17.set_down(Two_17)
    Three_Tail.set_down(Two_Tail)

    # level 1
    One_Head = HeadNode()
    One_3 = ElementNode(3)
    One_5 = ElementNode(5)
    One_7 = ElementNode(7)
    One_9 = ElementNode(9)
    One_11 = ElementNode(11)
    One_13 = ElementNode(13)
    One_15 = ElementNode(15)
    One_17 = ElementNode(17)
    One_Tail = TailNode()

    One_Head.set_next(One_3)
    One_3.set_next(One_5)
    One_5.set_next(One_7)
    One_7.set_next(One_9)
    One_9.set_next(One_11)
    One_11.set_next(One_13)
    One_13.set_next(One_15)
    One_15.set_next(One_17)
    One_17.set_next(One_Tail)

    Two_Head.set_down(One_Head)
    Two_3.set_down(One_3)
    Two_7.set_down(One_7)
    Two_11.set_down(One_11)
    Two_17.set_down(One_17)
    Two_Tail.set_down(One_Tail)

    # testing
    #print(my_list)

    # print(my_list.search(17))
    # my_list.remove(7)
    
    my_list.insert(2)
    #print(my_list)
    '''
    
if __name__ == "__main__":
    # testing
    my_skiplist = SkipList()
    for i in range(5):
        data = random.randint(0, 20)
        my_skiplist.insert(data)
        data = random.randint(0, 20)
        my_skiplist.insert(data)
        data = random.randint(0, 25)
        my_skiplist.remove(data)
    print(my_skiplist)
        
    for node in my_skiplist:
        print(node)
