JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: strassen

strassen: $(classes)

strassen.class: strassen.java
	$(JAVAC) $<
  
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: strassen" >> manifest.txt
	@echo "" >> manifest.txt

	jar cfm myjar.jar manifest.txt $(classes)

clean:
	rm -f *.class
	rm manifest.txt
	rm myjar.jar