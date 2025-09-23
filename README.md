# learn_java
Try to recap and learn.

# Compiling or running with dependent classes
a. Include curr folder name in package like `package learnJava;`

b.
```
javac Person.java Compare.java learn.java
java -cp ../ learn.java
```

# Enabling jdk src support
install jdk 21 src packages using pacman first
java 21 sdk extr from /usr/lib/jvm/java-21-openjdk/lib/src.zip
Run below to generate tags file there and link it to curr
```
echo "ctags --recurse=yes --exclude=.git --exclude=BUILD --exclude=.svn --exclude=vendor/* --exclude=node_modules/* --exclude=db/* --exclude=log/" >> README.md

// .vimrc
set tags+=/run/media/scion/PART2/java21Src/tags
```


