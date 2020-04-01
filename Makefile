JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: Strassen

Strassen: $(classes)

Strassen.class: Strassen.java
	$(JAVAC) $<
  
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: Strassen" >> manifest.txt
	@echo "" >> manifest.txt

	jar cfm myjar2.jar manifest.txt $(classes)

clean:
	rm -f *.class
	rm manifest.txt
	rm myjar.jar