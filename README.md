# XAIP-lib

*Authors:
[Giulia Brugnatti](https://github.com/giuliab97)*


**Some quick links**:
- [Documentation](https://giuliab97.github.io/xaip-lib/) (work in progress)

## Intro

This project, developed as part of the Master Thesis for the MSc in Computer Science and Engineering, consists of a pure Kotlin library for eXplainable AI planning (XAIP).

The project aims to implement a software proposal to create a model-based contrastive explanation within the XAIP domain.

Within this framework, the project is made up of four modules that will be described in the next lines:

- the `planning` module is a self-contained module containing the core entities to design and solve a planning problem.
Its abstractions are exploited within the different modules of the library to guarantee the coherence of the proposal.

- the key module of the library is the `explanation` module; it provides the core entities to enable users to inquire a planner about its decisions and retrieve explanations about them.

- the `dsl` module is a utility module designed to enhance the usage of the planning module.

- the `domain` module containing the implementation of two planning domains: the Block World and the Logistics domains.


## For users

This project is a work in progress, not yet intended for general-purpose usage.

## Experiments

### Proof of concept demonstration

We design a set of usage examples of the framework for each domain defined.
The end-to-end examples can be retrieved at directory `evaluation/src/test/kotlin/endtoendexample` and show how to create and instantiate domains and problems and also how to ask the system explanation about plans both exploiting the dsl and without using it.
The execution of each example produces either a *general* explanation, or a *minimal* one.

In the next lines, we will describe the general idea of the framework usage, besides giving some practical examples about how to enquire the system about its decisions and the kind of output a user should aspect.
In general, we can summarize the core steps the user must perform to get an explanation as follows:
- to begin, the user defines a model for a problem and a domain[^1];
- the user then instantiates an appropriate question specifying its parameters; commonly: the problem and the domain defined in the previous step, the plan retrieved from the automatic planner and the user's suggestions;
- consequently, the user must instantiate an `Explainer`; thus, the component responsible for building an explanation from a given question and an `ExplanationPresenter`, which is the entity entitled to show the explanation in a user-friendly way.

For example, let's assume we want to enquire the system about a solution proposed for a transportation problem within the Logistics domain.
We assume a user wants to move some objects that we call containers in a given location and wants to exploit an agent, which we call a robot, to do so.
More specifically we call:
- `l1`, ..., `l7`: the locations. Particularly, we arrange the locations as a graph shown following figure:
![logisticsGraph](https://user-images.githubusercontent.com/60556203/221888187-64ebd69f-6c67-43ba-a650-5a8549e6aed2.png)

- `c1` and `c2`: the two containers, that are respectively in location `l2` and `l3`;
- `r`: the robot agent that must move the containers `c1` and `c2` from their former locations to `l4` and `l1` respectively and eventually reach `l5`.

We assume that the initial solution proposed by the planner was:
```
val formerPlan = Plan.of(
            listOf(
                moveRfromL1toL3,
                loadC2fromL3onR,
                moveRfromL3toL1,
                unloadC2fromRtoL1,
                moveRfromL1toL2,
                loadC1fromL2onR,
                moveRfromL2toL4,
                unloadC1fromRtoL4,
                moveRfromL4toL5
            )
        )
```
Whereas a user wants to know why the robot did not go to `l6` to arrive at `l5`.
To force the system to check if there is a feasible solution for the robot to go to `l6` instead of `l4`, the user should define an appropriate question, `QuestionReplaceOperator`, instantiating it with the proper parameters as follows:
```
val question = QuestionReplaceOperator (
                problem ,
                formerPlan ,
                moveRfromL4toL6 ,
                8 ,
                alternativeState
              )
```
Next, create an `Explainer` and initialize it with a `Planner`:
```
val explainer = Explainer.of(Planner.strips())
```

asking the `Explainer` to explain the question:
```
val explanation = explainer.explain(question)
```
Finally, the user should instantiate an appropriate `ExplanationPresent` according to the type of explanation the user would like to receive. In this case, we assume the user would like a general explanation; to this extent, the user can create a `ExplanationPresenter` and require it to display the explanation:
```
ExplanationPresenter.of(explation).present()
```
In the next lines we see the output of this first call:
```
The problem [atLocation(r, l5), inContainerLocation(c1, l4), inContainerLocation(c2, l1)] is solvable.
The former plan was: [move(r, l1, l3), load(l3, c2, r), move(r, l3, l1),
    unload(l1, c2, r), move(r, l1, l2), load(l2, c1, r), move(r, l2, l4), 
    unload(l4, c1, r), move(r, l4, l5)]
The novel plan is: [move(r, l1, l3), load(l3, c2, r), move(r, l3, l1),
    unload(l1, c2, r), move(r, l1, l2), load(l2, c1, r), move(r, l2, l4), 
    unload(l4, c1, r), move(r, l4, l6), move(r, l6, l5)].
The novel plan is a valid solution to the problem.
The minimal solution is: [move(r, l1, l3), load(l3, c2, r), move(r, l3, l1), 
    unload(l1, c2, r), move(r, l1, l2), load(l2, c1, r), move(r, l2, l4), 
    unload(l4, c1, r), move(r, l4, l5)]
The plan is not the minimal solution.

There are 2 additional operators with respect to the minimal solution: 
    [move(r, l4, l6), move(r, l6, l5)].
```
The explanation demonstrates that it is possible to reach `l5` from `l6`. However, one can notice that a general explanation can be quite verbose whereas a user may want a more concise answer.
In such cases, the user can ask the system for a simplified version of the general explanation by calling \texttt{pre\-sentMi\-ni\-malEx\-pla\-na\-tion()} obtaining the following output:
```
The plan: [move(r, l1, l3), load(l3, c2, r), move(r, l3, l1), unload(l1, c2, r), move(r, l1, l2), load(l2, c1, r), move(r, l2, l4), unload(l4, c1, r), 
    move(r, l4, l6), move(r, l6, l5)], is valid: true
 The length is acceptable: true
 Operators missing: [move(r, l4, l5)]
 Additional operators: [move(r, l4, l6), move(r, l6, l5)]
``` 
The two explanations provide a similar insight into the property of the solution proposed, but the second one presents it more concisely.
To this extent, we design *minimal explanations* for proficient users who may not be interested in a detailed explanation in natural language.

[^1]: A set of problems for each domain defined is already available in the `domain` module.

## Benchmark
We evaluate the performance and execution time of our proposal in generating general and contrastive explanations for the questions. We formulated a set of valid plans of different lengths using ad-hoc scripts, generating over 5000 valid plans for the Block World and Logistics problems.

We then frame a set of questions for each type of explanation to evaluate the system's performance when required to add, remove, or replace operators in different positions on the plan or compare two solutions. We create two datasets (for contrastive and general explanations) containing over 8000 explanations for each question within each domain.

The analysis reveals that the resources needed for the elaboration of the explanations for the various questions in the two domains are barely different from one another.

There is no appreciable performance difference between the questions that require the compilation process and those that do not, or between the general explanation and the contrastive explanation.

### Step to reproduce
1. The first step to reproduce the experiments is to generate the benchmark. To do so it is just necessary to open a terminal, move to the root directory of the project and run:
```  ./gradlew :evaluation:generateCharts```
The command generates both the benchmark and its analysis.

The benchmarks file will be then available at `evaluation/res/benchmark`. The benchmark files will then be available at `evaluation/res/benchmark`. In total, there are 20 files in the benchmarks; they are organized as follows.
There are ten files relating to explanations of Block World problems; more precisely, we create a file for each type of question (remove action, add action, etc.) and explanation (contrastive, or general).
Following the same pattern as the previous lines, the second set of files analyzes problems in the Logistics domain.
Each file is organized as follows: 
 ```Domain, PlanLength, QuestionType, Time, Memory ```
The first column contains the name of the domain analyzed, the second the length of the plan considered, the third is the type of question, and the last two columns indicate respectively the amount of time (in ms) and memory (in bytes) required to calculate an explanation for a given question.

The benchmark analysis consists of two kinds of files: at `evaluation/res/benchmark/aggregate_data` will be available the aggregate data and at `res/benchmark/figures` the diagrams.
More specifically the first directory will contain four files: two regards provide the average memory occupation and execution time required from each domain (block_world_domain_aggregate.csv, logistics_domain_aggregate.csv) whereas the other provides a similar analysis comparing the performance of the system on the different questions (block_world_question_aggregate.csv, logistics_question_aggregate.csv).
The second on the other hand will contain 20 images each representing the time and memory variation for a specific question in case a general or minimal explanation is required.
