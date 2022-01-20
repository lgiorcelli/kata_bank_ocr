
<!DOCTYPE html>
<html>
<head lang="en-us">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <link href='https://fonts.googleapis.com/css?family=Oswald:700|Raleway|Poiret+One' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://codingdojo.org/css/codingdojo.css">
  <H1>Bank OCR - Coding Dojo</H1>
  <meta name="generator" content="Hugo 0.37.1" />

  
  <link rel="canonical" href="https://codingdojo.org/kata/BankOCR/">
  

  <meta property="og:url" content="https://codingdojo.org/kata/BankOCR/">
  <meta property="og:title" content="Coding Dojo">
  
  <meta name="apple-mobile-web-app-title" content="Coding Dojo">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

  <link rel="shortcut icon" type="image/x-icon" href="https://codingdojo.org/images/favicon.ico">
  <link rel="icon" type="image/x-icon" href="https://codingdojo.org/images/favicon.ico">
</head>

<body>
  <header class="main-header">
    <h1><a href=/>Coding Dojo</a></h1>
    <nav class="main-header__nav main-nav">
      <ul class="main-nav__list"><li class="main-nav__item">
          <a href="https://codingdojo.org/dojo/" class="main-nav__link">Dojos</a>
        </li><li class="main-nav__item main-nav__item--active">
          <a href="https://codingdojo.org/kata/" class="main-nav__link">Kata</a>
        </li><li class="main-nav__item">
          <a href="https://codingdojo.org/people/" class="main-nav__link">Peoples</a>
        </li><li class="main-nav__item">
          <a href="https://codingdojo.org/record/" class="main-nav__link">Records</a>
        </li><li class="main-nav__item">
          <a href="https://codingdojo.org/solution/" class="main-nav__link">Solutions</a>
        </li>
      </ul>
    </nav>
</header>

<div class="top-right-corner ribbon gradient">
  <div class="seams">
  <a class="" href="https://gitlab.com/codingdojo-org" title="View on Gitlab">
    Edit on Gitlab
  </a>
    </div>
</div>

  <article class="page">
    <div class="content">
      <h1>
        Bank OCR
      </h1>

      

<p>This Kata was presented at XP2006 by
<a href="https://codingdojo.org/people/EmmanuelGaillot">EmmanuelGaillot</a> and ChristopheThibaut .</p>

<p>Contents:</p>

<ul>
<li><a href="#problem-description">Problem Description</a></li>
<li><a href="#clues">Clues</a></li>
<li><a href="#suggested-test-cases">Suggested Test Cases</a></li>
<li><a href="#resources">Resources</a></li>
<li><a href="#comments-from-those-who-are-working-on-this-kata">Comments from those who are working on this Kata</a></li>
</ul>

<h2 id="problem-description">Problem Description</h2>

<h3 id="user-story-1">User Story 1</h3>

<p>You work for a bank, which has recently purchased an ingenious machine
to assist in reading letters and faxes sent in by branch offices. The
machine scans the paper documents, and produces a file with a number of
entries which each look like this:</p>

<pre><code>    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|
</code></pre>

<p>Each entry is 4 lines long, and each line has 27 characters. The first 3
lines of each entry contain an account number written using pipes and
underscores, and the fourth line is blank. Each account number should
have 9 digits, all of which should be in the range 0-9. A normal file
contains around 500 entries.</p>

<p>Your first task is to write a program that can take this file and parse
it into actual account numbers.</p>

<h3 id="user-story-2">User Story 2</h3>

<p>Having done that, you quickly realize that the ingenious machine is not
in fact infallible. Sometimes it goes wrong in its scanning. The next
step therefore is to validate that the numbers you read are in fact
valid account numbers. A valid account number has a valid checksum. This
can be calculated as follows:</p>

<pre><code>account number:  3  4  5  8  8  2  8  6  5
position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1

checksum calculation:
(d1+2*d2+3*d3+...+9*d9) mod 11 = 0
</code></pre>

<p>So now you should also write some code that calculates the checksum for
a given number, and identifies if it is a valid account number.</p>

<h3 id="user-story-3">User Story 3</h3>

<p>Your boss is keen to see your results. He asks you to write out a file
of your findings, one for each input file, in this format:</p>

<pre><code>457508000
664371495 ERR
86110??36 ILL
</code></pre>

<p>ie the file has one account number per row. If some characters are
illegible, they are replaced by a ?. In the case of a wrong checksum, or
illegible number, this is noted in a second column indicating status.</p>

<h3 id="user-story-4">User Story 4</h3>

<p>It turns out that often when a number comes back as ERR or ILL it is
because the scanner has failed to pick up on one pipe or underscore for
one of the figures. For example</p>

<pre><code>    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|
</code></pre>

<p>The 9 could be an 8 if the scanner had missed one |. Or the 0 could be
an 8. Or the 1 could be a 7. The 5 could be a 9 or 6. So your next task
is to look at numbers that have come back as ERR or ILL, and try to
guess what they should be, by adding or removing just one pipe or
underscore. If there is only one possible number with a valid checksum,
then use that. If there are several options, the status should be AMB.
If you still can&rsquo;t work out what it should be, the status should be
reported ILL.</p>

<h2 id="clues">Clues</h2>

<p>I recommend finding a way to write out 3x3 cells on 3 lines in your
code, so they form an identifiable digits. Even if your code actually
doesn&rsquo;t represent them like that internally. I&rsquo;d much rather read</p>

<pre><code>&quot;   &quot; +
&quot;|_|&quot; +
&quot;  |&quot;
</code></pre>

<p>than</p>

<pre><code>&quot;   |_|  |&quot; 
</code></pre>

<p>anyday.</p>

<p>When Christophe and Emmanuel presented this Kata at XP2005 they worked
on a solution that made extensive use of recursion rather than
iteration. Many people are more comfortable with iteration than
recursion. Try this kata both ways.</p>

<p>Some gotchas to avoid:</p>

<ul>
<li>Be very careful to read the definition of checksum correctly. It is not a simple dot product, the digits are reversed from what you expect.</li>
<li>The spec does not list all the possible alternatives for valid digits when one pipe or underscore has been removed or added</li>
<li>Don&rsquo;t forget to try to work out what a ? should have been by adding or removing one pipe or underscore.</li>
</ul>

<h2 id="suggested-test-cases">Suggested Test Cases</h2>

<p>If you want to just copy and paste these test cases into your editor, I
suggest first clicking &ldquo;edit this page&rdquo; so you can see the source. Then
you can be sure to copy across all the whitespace necessary. Just don&rsquo;t
save any changes by mistake.</p>

<pre><code>use case 1
 _  _  _  _  _  _  _  _  _ 
| || || || || || || || || |
|_||_||_||_||_||_||_||_||_|

=&gt; 000000000

  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |

=&gt; 111111111
 _  _  _  _  _  _  _  _  _ 
 _| _| _| _| _| _| _| _| _|
|_ |_ |_ |_ |_ |_ |_ |_ |_ 

=&gt; 222222222
 _  _  _  _  _  _  _  _  _ 
 _| _| _| _| _| _| _| _| _|
 _| _| _| _| _| _| _| _| _|

=&gt; 333333333

|_||_||_||_||_||_||_||_||_|
  |  |  |  |  |  |  |  |  |

=&gt; 444444444
 _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
 _| _| _| _| _| _| _| _| _|

=&gt; 555555555
 _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
|_||_||_||_||_||_||_||_||_|

=&gt; 666666666
 _  _  _  _  _  _  _  _  _ 
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |

=&gt; 777777777
 _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
|_||_||_||_||_||_||_||_||_|

=&gt; 888888888
 _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
 _| _| _| _| _| _| _| _| _|

=&gt; 999999999
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|

=&gt; 123456789

use case 3
 _  _  _  _  _  _  _  _    
| || || || || || || ||_   |
|_||_||_||_||_||_||_| _|  |

=&gt; 000000051
    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  | _ 
  | _||_||_||_|  |  |  | _|

=&gt; 49006771? ILL
    _  _     _  _  _  _  _ 
  | _| _||_| _ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _ 

=&gt; 1234?678? ILL

use case 4

  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |

=&gt; 711111111
 _  _  _  _  _  _  _  _  _ 
  |  |  |  |  |  |  |  |  |
  |  |  |  |  |  |  |  |  |

=&gt; 777777177
 _  _  _  _  _  _  _  _  _ 
 _|| || || || || || || || |
|_ |_||_||_||_||_||_||_||_|

=&gt; 200800000
 _  _  _  _  _  _  _  _  _ 
 _| _| _| _| _| _| _| _| _|
 _| _| _| _| _| _| _| _| _|

=&gt; 333393333 
 _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
|_||_||_||_||_||_||_||_||_|

=&gt; 888888888 AMB ['888886888', '888888880', '888888988']
 _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
 _| _| _| _| _| _| _| _| _|

=&gt; 555555555 AMB ['555655555', '559555555']
 _  _  _  _  _  _  _  _  _ 
|_ |_ |_ |_ |_ |_ |_ |_ |_ 
|_||_||_||_||_||_||_||_||_|

=&gt; 666666666 AMB ['666566666', '686666666']
 _  _  _  _  _  _  _  _  _ 
|_||_||_||_||_||_||_||_||_|
 _| _| _| _| _| _| _| _| _|

=&gt; 999999999 AMB ['899999999', '993999999', '999959999']
    _  _  _  _  _  _     _ 
|_||_|| || ||_   |  |  ||_ 
  | _||_||_||_|  |  |  | _|

=&gt; 490067715 AMB ['490067115', '490067719', '490867715']
    _  _     _  _  _  _  _ 
 _| _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|

=&gt; 123456789
 _     _  _  _  _  _  _    
| || || || || || || ||_   |
|_||_||_||_||_||_||_| _|  |

=&gt; 000000051
    _  _  _  _  _  _     _ 
|_||_|| ||_||_   |  |  | _ 
  | _||_||_||_|  |  |  | _|

=&gt; 490867715 
</code></pre>

<h2 id="resources">Resources</h2>

<p>One of the challenges that this kata poses is to convert the suggested test cases above
into code. If you are short on time and are using Ruby, you might want to use these prepared
fixtures:</p>

<ul>
<li>For Ruby: <a href="https://github.com/dvrensk/bank_ocr_kata">fixtures and minitest+rspec boilerplate</a>
from the <a href="https://www.meetup.com/Barcelona-on-Rails/events/gppkmnyxcbxb/">Barcelona on Rails</a>
group.</li>
</ul>

<h2 id="comments-from-those-who-are-working-on-this-kata">Comments from those who are working on this Kata</h2>

<p>Here you could write some thoughts about what you have learnt from this
Kata. If you want to discuss your approach and give some code samples,
you might want to link to a new page &ldquo;KataXByY &ldquo;. You don&rsquo;t have to post
all the code of your solution, or claim that it is the best possible
solution. What is interesting is your insights into the problem, and
decisions you made in the past.</p>

      <div class="article-footer">
        <a class="button" href="https://gitlab.com/codingdojo-org/codingdojo.org/-/edit/master/content/kata/BankOCR.md ">Edit</a>
      </div>
    </div>
  </article>
  <footer class="bottom">
  <nav>
    <ul>

      
      <li><a href="https://codingdojo.org/fr/">fr</a></li>
      
      <li><a href="https://codingdojo.org/">en</a></li>
      
      <li><a href="https://codingdojo.org/pt/">pt</a></li>
      
    </ul>
  </nav>
</footer>

</body>
</html>
