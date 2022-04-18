target:
	javac syntaxtree/*.java
	javac -classpath .:java-cup-11b.jar  Main.java 
	clear
	java -classpath .:java-cup-11b-runtime.jar Main $(file)

clean:
	rm *.class