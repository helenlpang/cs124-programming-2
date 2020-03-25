JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: randmst

randmst: $(classes)

randmst.class: randmst.java
	$(JAVAC) $<
  
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: randmst" >> manifest.txt
	@echo "" >> manifest.txt

	jar cfm myjar.jar manifest.txt $(classes)

clean:
	rm -f *.class
	rm manifest.txt
	rm myjar.jar