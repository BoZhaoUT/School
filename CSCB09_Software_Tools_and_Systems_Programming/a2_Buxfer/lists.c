#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lists.h"

Group *create_group(const char *group_name);
User *create_user(const char *user_name);
Xct *create_transaction(const User *payer, double amount);


/* Add a group with name group_name to the group_list referred to by 
* group_list_ptr. The groups are ordered by the time that the group was 
* added to the list with new groups added to the end of the list.
*
* Returns 0 on success and -1 if a group with this name already exists.
*
* (I.e, allocate and initialize a Group struct, and insert it
* into the group_list. Note that the head of the group list might change
* which is why the first argument is a double pointer.) 
*/
int add_group(Group **group_list_ptr, const char *group_name) {
	int result;
	// create a new group with name group_name
	Group *new_group;
	Group *curr = *group_list_ptr;
	// in case the group_list is empty
	if (*group_list_ptr == NULL) {
		new_group = create_group(group_name);
		*group_list_ptr = new_group; 
		result = 0;
	// check if the name of the first group is identical to group_name
	} else if (strcmp(curr->name, group_name) == 0){
		result = -1;
    // assume the group_list is non-mepty
	} else {
		// loop through the group list until it reaches the end or find a
		// group with the same name
		while (curr->next != NULL && (strcmp(curr->name, group_name) != 0)) {
			curr = curr->next;
		}
		// in case there is a group with the same name
		if (strcmp (curr->name, group_name) == 0) {
			result = -1;
		// in case we reach the tail of group list and 
		// there are no groups with the same name
		} else {
			new_group = create_group(group_name);
			curr->next = new_group;
			result = 0;
		}
	}
	return result;
}


/* 
* Create a new group with group name with name group_name.
* This helper function should be called by add_group.
*/
Group *create_group(const char *group_name) {
	Group *new_group;
	char *name;
	new_group = malloc( sizeof(Group));
	if (new_group == NULL) {
		fprintf(stderr, "Error: %s\n", "malloc failed");
		exit(EXIT_FAILURE);
	}
	// set its name
	name = malloc( strlen(group_name) + 1);
	if (name == NULL) {
		fprintf(stderr, "Error: %s\n", "malloc failed");
		exit(EXIT_FAILURE);
	}
	strcpy(name, group_name);
	new_group->name = name;
	// set its users, transactions and next
	new_group->users = NULL;
	new_group->xcts = NULL;
	new_group->next = NULL;
	return new_group;
}


/* Print to standard output the names of all groups in group_list, one name
*  per line. Output is in the same order as group_list.
*/
void list_groups(Group *group_list) {
	Group *curr = group_list;
	if (group_list == NULL) {
		printf("The group list is empty.\n");
	} else {
		// loop through the entire list of groups
		while (curr != NULL) {
			printf("%s\n", curr->name);
			curr = curr->next;
		}
	}
}


/* Search the list of groups for a group with matching group_name
* If group_name is not found, return NULL, otherwise return a pointer to the 
* matching group list node.
*/
Group *find_group(Group *group_list, const char *group_name) {
	Group *curr = group_list;
	Group *result;
	// in case the group is empty
	if (group_list == NULL) {
		result = NULL;
	// assume this group is not empty
	} else {
		int keep_searching = 1;
		while (keep_searching) {
			// in case we reach the end of this group list
			if (curr == NULL) {
				keep_searching = 0;
				result = NULL;
			}
			// in case we find the group with name group_name
			else if (strcmp(curr->name, group_name) == 0) {
				keep_searching = 0;
				result = curr;
			} else {
				curr = curr->next;
			}
		}
	}
    return result;
}

/* 
* Create a new group with group name with name group_name.
* This helper function should be called by add_user.
*/
User *create_user(const char *user_name) {
	User *new_user;
	char *name;
	new_user = malloc( sizeof(User));
	if (new_user == NULL) {
		fprintf(stderr, "Error: %s\n", "malloc failed");
		exit(EXIT_FAILURE);
	}
	// set its name
	name = malloc( strlen(user_name) + 1);
	if (name == NULL) {
		fprintf(stderr, "Error: %s\n", "malloc failed");
		exit(EXIT_FAILURE);
	}
	strcpy(name, user_name);
    new_user->name = name;
    // set its balance and next
    new_user->balance = 0.0;
    new_user->next = NULL;
    return new_user;
}


/* Add a new user with the specified user name to the specified group. Return zero
* on success and -1 if the group already has a user with that name.
* (allocate and initialize a User data structure and insert it into the
* appropriate group list)
*/
int add_user(Group *group, const char *user_name) {
	int result;
	User *new_user;

	User *prev_user = find_prev_user(group, user_name);
	// in case there is no users with the same name

	if (prev_user == NULL) {

		new_user = create_user(user_name);
		// update links bettween gruop and existing users
		new_user->next = group->users;
		group->users = new_user;
		result = 0;
	// in case there is one user with the same name
	} else {
		result = -1;
	}
    return result;
}

/* Remove the user with matching user and group name and
* remove all her transactions from the transaction list. 
* Return 0 on success, and -1 if no matching user exists.
* Remember to free memory no longer needed.
* (Wait on implementing the removal of the user's transactions until you 
* get to Part III below, when you will implement transactions.)
*/
int remove_user(Group *group, const char *user_name) {
	User *prev_user = find_prev_user(group, user_name);
	User *removed_user;
	int result;
	// in case this user doesn't exist
	if (prev_user == NULL) {
		result = -1;
	// assume this user exists
	} else {
		// remove this user
		result = 0;
		// remove all her transactions
		remove_xct(group, user_name);
		// in case this user is the first user in users list
		if (strcmp(group->users->name, user_name) == 0) {
			removed_user = prev_user;
			group->users = prev_user->next;
		// this user is not the first user in users list
		} else {
			removed_user = prev_user->next;
			prev_user->next = prev_user->next->next;
		}
		free(removed_user->name);
		free(removed_user);
	} 
    return result;
}

/* Print to standard output the names of all the users in group, one
* per line, and in the order that users are stored in the list, namely 
* lowest payer first.
*/
void list_users(Group *group) {
	User *curr = group->users;
	// in case there are no users in this group
	if (curr == NULL) {
		printf("The users list is empty.\n");
	// in case there is at least one user in this grouop
	} else {
		// loop through the users list
		while (curr != NULL) {
			printf("%s\n", curr->name);
			curr = curr->next;
		}
	}
}

/* Print to standard output the balance of the specified user. Return 0
* on success, or -1 if the user with the given name is not in the group.
*/
int user_balance(Group *group, const char *user_name) {
	int result;
	User *prev_user;
	// if there are no users in this group
	if (group->users == NULL) {
		result = -1;
	// assume this group has at least one user
	} else {
		prev_user = find_prev_user(group, user_name);
		// the user with user_name doesn't exist in this group
		if (prev_user == NULL) {
			result = -1;
		}
		// the user with user_name is the first user in this group
		else if (strcmp(prev_user->name, user_name) == 0) {
			result = 0;
			printf("The balance of user %s is %g \n", prev_user->name ,prev_user->balance);
		// prev_user's next is the user with user_name
		} else {
			result = 0;
			printf("The balance of user %s is %g \n", prev_user->next->name ,prev_user->next->balance);
		}
	}
    return result;
}

/* Print to standard output the name of the user who has paid the least 
* If there are several users with equal least amounts, all names are output. 
* Returns 0 on success, and -1 if the list of users is empty.
* (This should be easy, since your list is sorted by balance). 
*/
int under_paid(Group *group) {
	int result;
	double least_balance;
	User *curr;
	// if there are no users in this gropu
	if (group->users == NULL) {
		result = -1;
	// assume there is at least one user in this group
	} else {
		least_balance = group->users->balance;
		curr = group->users;
		// loop through users with balance least_balance
		printf("under paid user(s): ");
		while (curr != NULL && curr->balance == least_balance) {
			printf("%s ", curr->name);
			curr = curr->next;
		}
		printf("\n");
		result = 0;
	}
    return result;
}

/* Return a pointer to the user prior to the one in group with user_name. If 
* the matching user is the first in the list (i.e. there is no prior user in 
* the list), return a pointer to the matching user itself. If no matching user 
* exists, return NULL. 
*
* The reason for returning the prior user is that returning the matching user 
* itself does not allow us to change the user that occurs before the
* matching user, and some of the functions you will implement require that
* we be able to do this.
*/
User *find_prev_user(Group *group, const char *user_name) {
	User *curr = group->users;
	User *result;
	int keep_searching = 1;
	if (group == NULL) {
		result = NULL;
	} else {
		// in case there are no users
		if (group->users == NULL) {
			result = NULL;
		// assume there is at least one user in users list
		} else {
					// if the first user is the user with user_name in the users list
	    if (strcmp(curr->name, user_name) == 0) {
		    result = curr;
		// assume the first user is not the one with user_name
	    } else {
		    while (keep_searching) {
		        // in case we reach the end of users list
		        if (curr->next == NULL) {
			        keep_searching = 0;
			        result = NULL;
		        }
		        // in case next user with name user_name
		        else if (strcmp(curr->next->name, user_name) == 0) {
		    	    keep_searching = 0;
			        result = curr;
		        } else {
			        curr = curr->next;
		        }
	        }
	    }
		}

	}
    return result;
}

/* Add the transaction represented by user_name and amount to the appropriate 
* transaction list, and update the balances of the corresponding user and group. 
* Note that updating a user's balance might require the user to be moved to a
* different position in the list to keep the list in sorted order. Returns 0 on
* success, and -1 if the specified user does not exist.
*/
int add_xct(Group *group, const char *user_name, double amount) {
	int result;
	Xct *new_transaction;
	User *user;
	User *curr;
	double new_balance;
	User *prev_user = find_prev_user(group, user_name);
	// the specified user does not exist
	if (prev_user == NULL) {
		result = -1;
	// assume the specified user exists in this group
	} else {
		result = 0;
		// find the user who paid the expense
		if (strcmp(group->users->name, user_name) == 0) {
			user = group->users;
			prev_user = NULL;
		} else {
			user = prev_user->next;
		}
		// add a new transaction
		new_transaction = create_transaction(user, amount);
		new_transaction->next = group->xcts;
		group->xcts = new_transaction;
		// update the balance of this user
		new_balance = user->balance + amount;
		user->balance = new_balance;
		// resort users list if there is at least one user behind this 
		// user and the next user's balance is less than current user's
		// balance
		if ((user->next != NULL) && (new_balance > user->next->balance)) {
			// remove this user from the users list
			// case1: this user is the first user in users list
			if (prev_user == NULL) {
				group->users = user->next;
			// case2: this user is not the first user in users list
			} else {
				prev_user->next = user->next;
			}
			// loop through the rest of users list untill it reaches
			// the end of the users list or it finds a user with
			// the same or highier balance
			curr = user->next;
			int keep_searching = 1;
			while (keep_searching) {
				// in case it reaches the end of the list
				if (curr->next == NULL) {
					keep_searching = 0;
					curr->next = user;
					user->next = NULL;
				// in case if next user's balance is greater or
				// equal to the user's balance
		        } else if (curr->next->balance >= new_balance) {
		        	keep_searching = 0;
		        	user->next = curr->next;
		        	curr->next = user;
		        } else {
		        	curr = curr->next;
		        }
		    }
		}
	}
    return result;
}


/*
* Create a new transaction with payer name and amount.
* This helper function should be called by add_xct.
*/
Xct *create_transaction(const User *payer, double amount) {
	Xct *new_transaction;
	new_transaction = malloc( sizeof(Xct));
	if (new_transaction == NULL) {
		fprintf(stderr, "Error: %s\n", "malloc failed");
		exit(EXIT_FAILURE);
	}
	// set payer of this transaction
	new_transaction->name = payer->name;
	// set it's amount and next
	new_transaction->amount = amount;
	new_transaction->next = NULL;
	return new_transaction;
}


/* Print to standard output the num_xct most recent transactions for the 
* specified group (or fewer transactions if there are less than num_xct 
* transactions posted for this group). The output should have one line per 
* transaction that prints the name and the amount of the transaction. If 
* there are no transactions, this function will print nothing.
*/
void recent_xct(Group *group, long num_xct) {
	Xct *curr = group->xcts;
	long count = 0;
	// in case there is at least one transaction
	while (curr != NULL && count < num_xct) {
		printf("%s paid %.2f \n", curr->name, curr->amount);
		curr = curr->next;
		count++;
	}
}

/* Remove all transactions that belong to the user_name from the group's 
* transaction list. This helper function should be called by remove_user. 
* If there are no transactions for this user, the function should do nothing.
* Remember to free memory no longer needed.
*/
void remove_xct(Group *group, const char *user_name) {
	Xct *curr = group->xcts;
	Xct *removed_xct;
	// in case there is at least one transaction
	if (curr != NULL) {
		// loop through 2nd to last transaction
		while (curr->next != NULL) {
			// if next transaction is paid by this user
			// then remove next transaction
			if (strcmp(curr->next->name, user_name) == 0) {
				removed_xct = curr->next;
				curr->next = curr->next->next;
				free(removed_xct);
			} else {
				curr = curr->next;
			}
		}
		// check if the first transaction is paid by this user
		if (strcmp(group->xcts->name, user_name) == 0) {
			removed_xct = group->xcts;
			group->xcts = group->xcts->next;
			free(removed_xct);
		}
	}
}
