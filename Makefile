default: c

executables := $(notdir $(basename $(wildcard src/main/scala/*.scala)))
emulatorDirectory := ./emulator
verilogDirectory := ./verilog

c:
	sbt "run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem --targetDir ${emulatorDirectory}"
# autodetect file changes and rerun
cc:
	sbt "~run --backend c --compile --genHarness --test --debug --vcd --debugMem --vcdMem --targetDir ${emulatorDirectory}"
v:
	sbt "run --backend v --genHarness --targetDir ${verilogDirectory}"

# autodetect file changes and rerun
vv:
	sbt "~run --backend v --genHarness --targetDir ${verilogDirectory}"


clean:
	-rm -f $(executables)				# remove simulator binaries
	-rm -rf target*
	-rm -rf project/target*
	-rm -rf ${emulatorDirectory}			# remove simulator source files

clean_all:
	-rm -f $(executables)				# remove simulator binaries
	-rm -rf target*
	-rm -rf project/target*
	-rm -rf ${emulatorDirectory}			# remove simulator source files
	-rm -rf ${verilogDirectory}			# remove verilog source files

.PHONY: all c cc v vv clean clean_all
