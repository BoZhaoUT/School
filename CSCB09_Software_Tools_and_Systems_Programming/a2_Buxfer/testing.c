#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct group {
	char *name;
};


struct group* CreateGroup(char *name) {
	struct group *result;
	result = malloc( sizeof(struct group));

	result->name = name;
	return result;
}

int main() {

	//struct group *new_group;
	//struct group *other_group;
	char *s1 = "vincent";
	//char *s2 = malloc(8);
	char *s2 = "vincent";

	//char n[3] = {'f', 'i', '\0'};
	//char n2[3] = "ab";
	//n2[1] = 'c';

	//printf("n2 is %s \n", n2);
	
	
	if (s1 == s2) {
		printf("%s\n", "yes");
	} else {
		printf("not equal\n");
	}

    //new_group = CreateGroup("fi");
    //other_group = CreateGroup(n);

    /*
	printf("%s \n", new_group->name);
	printf("%s \n", other_group->name);

	if (new_group->name == other_group->name) {
		printf("yes\n");
	} else {
		printf("no \n");
	}
	
    */
    return 0;
}