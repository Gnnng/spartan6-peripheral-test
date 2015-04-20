default: c

c:
	sbt "run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem"

cc:
	sbt "~run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem"

v:
	sbt "run --backend v --genHarness"

vv:
	sbt "~run --backend v --genHarness"


clean:
	rm -rf target*
	rm -rf project/target*
	rm -f *.cpp *.v *.h *.o

.PHONY: all c cc v vv clean
