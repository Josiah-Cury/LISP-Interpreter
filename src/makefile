JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Interpreter.java \
	Parser.java \
	Executioner.java \
	Token.java \
	Operator.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
