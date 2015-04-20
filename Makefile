default: c
executables := $(notdir $(basename $(wildcard src/main/scala/*.scala)))
c:
	sbt "run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem"
# autodetect file changes and rerun
cc:
	sbt "~run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem"
v:
	sbt "run --backend v --genHarness"

# autodetect file changes and rerun
vv:
	sbt "~run --backend v --genHarness"

clean:
	rm -f $(executables)				# remove simulator binaries
	rm -rf target*
	rm -rf project/target*
	rm -f *.cpp *.h *.o					# remove simulator source files

clean_all:
	rm -f $(executables)				# remove simulator binaries
	rm -rf target*
	rm -rf project/target*
	rm -f *.cpp *.h *.o					# remove simulator source files
	rm -f *.vcd *.v						# remove verilog source files

.PHONY: all c cc v vv clean clean_all
