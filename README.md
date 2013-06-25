Align three sequence using Quadratic space
===========================================

Space not running time is often the factor limiting the application of dynamic programming. 
Often cubic space is required to align three sequences (3D dynamic programming). Given this 
space requirement, alignment of three genes with a length of more than 1000 characters is 
almost infeasible in most of our modern computers. Here, by using a divide conque technique, 
I implemented alignment of three sequences in quadratic space. The idea of this implementation 
is similar to Hirschberg\'s algorithm http://en.wikipedia.org/wiki/Hirschberg's_algorithm, which 
I have an implmentation in Python, see https://github.com/wuzhigang05/Dynamic-Programming-Linear-Space.git


See run{1,2,3,4,5}.sh for the usage of the program


Installation
============
    make


Example Usage
=============

    java  AlignThreeSequence NM_000558 NM_008218 NM_013096

Above commands will write a file named "NM_000558_NM_008218_NM_013096" to your current working directory.
This file contains the alignment for these three sequence file. Also, these files have to be in Fasta format. See
here if you don' know what is a Fasta format (http://en.wikipedia.org/wiki/FASTA_format).

Author
=========
Zhigang Wu

Deparment of Botany and Plant Sciences

University of California Riverside


LICENSE
=========
Copyright (c) <2013>, <Zhigang Wu>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    1. Redistributions of source code must retain the above copyright notice, 
       this list of conditions and the following disclaimer.
    
    2. Redistributions in binary form must reproduce the above copyright 
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. Neither the name of Django nor the names of its contributors may be used
       to endorse or promote products derived from this software without
       specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
