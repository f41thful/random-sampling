# random-sampling
You provide the software with a population distribution and size and the number of samples you want to take from that 
distribution. This distribution will have n clusters (a cluster is just a class which has a certain distribution or
representation in the population).

It outputs the sampling distribution and repetition ratio for each of the clusters. The software will also output the 
percentual difference with the actual distribution.

You can repeat the process x times and check how many of those times there was any cluster whose
error was greater than a certain bias (in percentual difference). In addition, you will get the mean and standard deviation
for those x times.

The project is thouroughly tested. Tests were developed first. Error detection was of high concern so
that no invalid data go through.

The actual output of the program is units over 1.

## Execution
You can execute the project with the class **com.coding2go.executables.MainWithRepetition**.

## Example
We can see in the following table the outcome of the Spanish elections of 2019 (second ones) along
with sampling distribution output by the software depending on the number of samples.

| Party | 1 | 2 | 3 | 4 | 5 | 6 | Others | 
| ------|:---:|:---:|:---:|:---:|:---:|:---:|-----:|
| Actual (%) | 28 | 20.82 | 15.09 | 9.8 | 3.61 | 6.79 | 14.96 | 
|10| samples| 20| 0| 40| 20| 10| 0| 10| 
|50| samples| 26| 12| 14| 8| 2| 8| 30| 
|100| samples| 27| 19| 20| 10| 4| 9| 10| 
|400| samples| 26| 21.75| 15.25| 11| 4| 6| 14.5| 
|4000| samples| 27.82| 20.27| 15.65| 9.75| 3.82| 6.75| 15.12| 
|10000| samples| 27.77| 20.73| 15.13| 9.5| 3.78| 7.19| 14.86| 
|40000| samples| 28.42| 20.78| 14.95| 9.72| 3.4| 6.63| 15.12|
