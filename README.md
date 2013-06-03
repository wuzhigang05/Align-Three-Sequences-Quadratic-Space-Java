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



Author
=========
Zhigang Wu

Deparment of Botany and Plant Sciences

University of California Riverside


LICENSE
=========
Copyright (c) <2013>, <Zhigang Wu>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
   must display the following acknowledgement:
   This product includes software developed by the <Zhigang Wu>.
4. Neither the name of the <Zhigang Wu> nor the
   names of its contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY <COPYRIGHT HOLDER> ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
