# Introduction

Solidty-Artist can be used to generate Solidity source code files within Java applications. The syntax is similar to square/javapoet (https://github.com/square/javapoet).

However, Solidity-Artist is build using the Visitor Pattern. This allows for easy addition of new language features. Right now Solidity-Artist is full compatible up to Solidity@0.6

# Usage

First, I recommend to define all required objects for your Solidity file such as the PragmaElement, ImportElement, FunctionElements, etc. Afterwards, you can easily inject all elements via the ```FileElement.builder()``` call.

Then just create a ```SolidityFile``` and use the ```saveToFile()``` function to generate your Solidity smart contract.

You can have a look in the ```SolidityFileTests.java``` test file for a large example.

# TODOs

+ Comments for parameters in structs
+ NullPointerExceptions occur on some empty functions
+ Comment at the end of a contract or library
+ Calldata as DataLocation
+ Override and virtual keywords