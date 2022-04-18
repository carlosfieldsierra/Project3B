# Project 3b: MiniJava Errors & IR 
Complies MiniJava code to quadruples of three-address code as intermediate
representation. With error handling to the parser and create a semantic analyzer.
## Run Instructions

To print out the intermediate representation of a MiniJava file execute the command below, where the path to the file you wish to run is placed after `file=`
```bash
make file=BubbleSort.java
```

## Issues
- I was not able to fully make the `TypeCheckVistor` so as it stands it does not check for errors related to types
- If the edge case where `If (exp){} else {}` has empty blocks then the IR labels don't go to the right places



## Example Test
I found this [site](http://minijava.azurewebsites.net/) online which contains many MiniJava files. I tested based on these files. Below is one example I tested of bubble sort in MiniJava. Below is the BubbleSort.java
```
class BubbleSort {
    public static void main(String[] a) {
        System.out.println(new BBS().Start(10));
    }
}


// This class contains the array of integers and
// methods to initialize, print and sort the array
// using Bublesort
class BBS {

    int[] number;
    int size;

    // Invoke the Initialization, Sort and Printing
    // Methods
    public int Start(int sz) {
        int aux01;
        aux01 = this.Init(sz);
        aux01 = this.Print();
        System.out.println(99999);
        aux01 = this.Sort();
        aux01 = this.Print();
        return 0;
    }


    // Sort array of integers using Bublesort method
    public int Sort() {
        int nt;
        int i;
        int aux02;
        int aux04;
        int aux05;
        int aux06;
        int aux07;
        int j;
        int t;
        i = size - 1;
        aux02 = 0 - 1;
        while (aux02 < i) {
            j = 1;
            //aux03 = i+1 ;
            while (j < (i + 1)) {
                aux07 = j - 1;
                aux04 = number[aux07];
                aux05 = number[j];
                if (aux05 < aux04) {
                    aux06 = j - 1;
                    t = number[aux06];
                    number[aux06] = number[j];
                    number[j] = t;
                } else nt = 0;
                j = j + 1;
            }
            i = i - 1;
        }
        return 0;
    }

    // Printing method
    public int Print() {
        int j;
        j = 0;
        while (j < (size)) {
            System.out.println(number[j]);
            j = j + 1;
        }
        return 0;
    }

    // Initialize array of integers
    public int Init(int sz) {
        size = sz;
        number = new int[sz];

        number[0] = 20;
        number[1] = 7;
        number[2] = 12;
        number[3] = 18;
        number[4] = 2;
        number[5] = 11;
        number[6] = 6;
        number[7] = 9;
        number[8] = 19;
        number[9] = 5;

        return 0;
    }

}
```
Here is the IR
```
t0 := new BBS
param t0
param 10
t1 := call Start, 2
param t1
call System.out.println, 1
param this
param sz
t2 := call Init, 2
aux01 := t2
param this
t3 := call Print, 1
aux01 := t3
param 99999
call System.out.println, 1
param this
t4 := call Sort, 1
aux01 := t4
param this
t5 := call Print, 1
aux01 := t5
return 0
t6 := size - 1
i := t6
t7 := 0 - 1
aux02 := t7
t8 := aux02 < i
L0:
iffalse t8 goto L1
j := 1
t9 := i + 1
t10 := j < t9
L2:
iffalse t10 goto L3
t11 := j - 1
aux07 := t11
t12 := number[aux07]
aux04 := t12
t13 := number[j]
aux05 := t13
t14 := aux05 < aux04
iffalse t14 goto L4
t15 := j - 1
aux06 := t15
t16 := number[aux06]
t := t16
t17 := number[j]
number[aux06] := t17
number[j] := t
goto L5
L4:
nt := 0
t18 := j + 1
j := t18
goto L2
L3:
t19 := i - 1
i := t19
goto L0
L1:
return 0
j := 0
t20 := j < size
L6:
iffalse t20 goto L7
t21 := number[j]
param t21
call System.out.println, 1
t22 := j + 1
j := t22
goto L6
L7:
return 0
size := sz
t23 := new int, sz
number := t23
number[0] := 20
number[1] := 7
number[2] := 12
number[3] := 18
number[4] := 2
number[5] := 11
number[6] := 6
number[7] := 9
number[8] := 19
number[9] := 5
return 0
```