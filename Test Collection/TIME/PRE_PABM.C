static char rcsid[] = "$Header$";

#include <stdio.h>
#include <ctype.h>
#define TRUE 1
#define FALSE 0

#define BEGIN_DOC_SECTION 'i'
#define END_DOC_SECTION 'o'

#define DEFAULT_SECTION 'a'
#define INITIAL_SECTION 'X'
#define DISCARD_SECTION 'X'

char *copy(), *discard(), *copy_lc();

/* Structure giving the "section_designators": those strings appearing */
/* at the beginning of a line which indicates a new section begins. */
/* The names of the section_designators must be sorted in reverse */
/* alphabetic order, and the last name must be the empty string */

struct sectionstr {
    char *name;                 /* String that appears at beginning of a */
                                /* line indicating a new section starts */
                                /* (and the old one ends) */
    int  name_len;              /* Length of name that needs to match */
    char *(*parse)();           /* Procedure to handle this section. */
                                /* Normally 'copy' or 'discard' unless */
                                /* special attention is needed */
    char id;                    /* One char section designator (see */
                                /* the index_spec file for this collection) */
                                /* If id matches DISCARD_SECTION, then the */
                                /* section is not output for parsing. */
    char one_line;              /* Boolean flag. If set, this entire */
                                /* section is on the present line */
    char begin_doc;             /* Boolean flag. If set, this section */
                                /* begins a new document within this file */
    char include_all;           /* Boolean flag. If set, "name" should be */
                                /* part of the section for display purposes */
                                /* Note that "name" is never output for */
                                /* parsing. */
} section [] = {
    "*TEXT",       5,  discard,  'X', TRUE,  TRUE, FALSE,
    "*STOP",       5,  discard,  'X', TRUE,  FALSE, FALSE,
    "*FIND",       5,  discard,  'X', TRUE,  TRUE, FALSE,
            /* Note: guaranteed no match */
    "",            1,  copy_lc,     'a', FALSE, FALSE, FALSE
};

int num_sections = sizeof (section) / sizeof (section[0]) - 1;


main (argc, argv)
int argc;
char *argv[];
{
    register int i;
    register char *next_c;
    register int current_section;
    int init_index;
    int default_index;
    int section_length;
    
    int new_section;
    
    int doc_offset;             /* number of chars in current_file seen */
    FILE *file_desc;            /* file descriptor for current input
                                /* document */
    char buffer[1024];
    char file_name[1024];
    int temp;

    /* Find indices of INITIAL_SECTION and DEFAULT_SECTION */
    init_index = num_sections;
    default_index = num_sections;
    for (i = 0; i < num_sections; i++) {
         if (section[i].id == INITIAL_SECTION) {
             init_index = i;
         }
         if (section[i].id == DEFAULT_SECTION) {
             default_index = i;
         }
     }

    while (NULL != gets (file_name)) {
        if ((file_desc = fopen(file_name, "r")) == NULL) {
            fprintf (stderr,"pre_parser: Can't open file %s - Ignored\n",
                     file_name);
            continue;
        }

        doc_offset = 0;
        
        printf (".%c 0 %s\n", BEGIN_DOC_SECTION, file_name);
        if (section[init_index].id != DISCARD_SECTION) {
            printf (".%c 0\n", INITIAL_SECTION);
        }
        current_section = init_index;
        
        while (NULL != (fgets(buffer, 1024, file_desc))) {
            next_c = buffer;

            /* Determine if this line starts a new section by comparing it
            /* with the section starters contained in section */
            /* Note section is sorted in descending alphabetic order */
            i = 0;
            while (*next_c < section[i].name[0]) {
                i++;
            }
            if (*next_c == section[i].name[0]) {
                while (0 > (temp = strncmp (next_c, 
                                            section[i].name, 
                                            section[i].name_len))) {
                    i++;
                }
                if (temp == 0) {
                    /* Have found new section starter. */
                    new_section = 1;
                    
                    /* section starter is never output (so will not be */
                    /* indexed) */
                    section_length = strlen (section[i].name);
                    next_c += section_length;

                    /* Check to see if this is a new document */
                    if (section[i].begin_doc) {
                        /* Output end of old document and beginning of */
                        /* new document (unless at start of file) */
                        if (doc_offset != 0) {
                            printf ("\n.%c %d %d\n",
                                    END_DOC_SECTION,
                                    doc_offset,
                                    doc_offset);
                            printf (".%c %d %s\n",
                                    BEGIN_DOC_SECTION,
                                    doc_offset,
                                    file_name);
                        }
                        current_section = init_index;
                    }

                    /* Check to see if need new section title */
                    if (section[current_section].id != section[i].id ||
                        section[i].begin_doc) {
                        current_section = i;
                        if (section[i].id != DISCARD_SECTION) {
                            /* Output the new section header, including */
                            /* offset of end of previous section and */
                            /* beginning of this one */
                            printf (".%c %d %d\n", 
                                    section[i].id, 
                                    doc_offset, 
                                    section[i].include_all 
                                           ? doc_offset
                                           : doc_offset + section_length);
                        }
                    }
                }
            }
            /* Check to see if previous section was a "one-line" section, */
            /* in which case a new section must be output */
            if (new_section) {
                new_section = 0;
            }
            else {
                if ( section[current_section].one_line &&
                     section[current_section].id != DEFAULT_SECTION) {
                    current_section = default_index;
                    printf (".%c %d %d\n", 
                            section[current_section].id,
                            doc_offset,
                            doc_offset);
                }
            }


        
            /* Perform any necessary parsing action on this line. */
            /* The parsing procedure is given by */
            /*      section[current_section].parse */
            /* which is a procedure taking the current position in buffer, */
            /* and returning the position following the new-line (or eof) */
            next_c = section[current_section].parse (next_c);
            
            /* Update the doc_offset for this line */
            doc_offset += next_c - buffer;
        }

        /* Indicate end of document */
        /* (First \n in case document ended without newline) */
        printf ("\n.%c %d\n", END_DOC_SECTION, doc_offset);
        fclose (file_desc);
    }

    exit (0);
}

        
static char *
copy (ptr)
register char *ptr;
{
    /* Make sure actual text line does not begin with a dot */
    /* by adding an extra space there. */
    if (*ptr) {
        putchar (' ');
    }

    while (*ptr) {
        /* Do not output non-ascii characters (the parser objects) or */
        /* control-characters */
        if (isascii (*ptr) && (isprint (*ptr) || isspace (*ptr))) {
            putchar (*ptr);
        }
        ptr++;
    }
    return (ptr);
}

static char *
copy_lc (ptr)
register char *ptr;
{
    /* Make sure actual text line does not begin with a dot */
    /* by adding an extra space there. */
    if (*ptr) {
        putchar (' ');
    }

    while (*ptr) {
        /* Do not output non-ascii characters (the parser objects) or */
        /* control-characters */
        if (isascii (*ptr) && (isprint (*ptr) || isspace (*ptr))) {
            if (isupper (*ptr)) {
                putchar (tolower (*ptr));
            }
            else {
                putchar (*ptr);
            }
        }
        ptr++;
    }
    return (ptr);
}

static char *
discard (ptr)
register char *ptr;
{
    if (*ptr) {
        while (*(++ptr))
            ;
    }
    return (ptr);
}
