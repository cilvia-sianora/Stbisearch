#!/bin/tcsh
foreach i (ohsumed.*)
  gzip $i
end
