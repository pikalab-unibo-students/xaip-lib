# XAIP-lib

*Authors:
[Giulia Brugnatti](https://github.com/giuliab97)*

This project, developed as part of the Master Thesis for the Msc in Computer Science and Engineering, 
consists of a library for eXplainable AI planning (XAIP).

The aim of the project is to implement a software proposal to create model-based contrastive explanation 
within the XAIP domain.

Within this framework, the project is made up of four modules that will be described in the next lines.

## Planning
The planning module is a self-contained modules containing the core entities to design and solve a planning problem.
Its abstractions are exploited within the different modules of the library to guarantee the coherence of the proposal.

## Explanation
The core module of the library it provides the core entities to enable users to inquiry a planner about its decisions.

## DSL
The DSL is a utility moduly design enhance the usage of the planning module.

## Domain 
Modules containing the implementation of two planning domains: the Block World and the Logistic domains.
