dir=$(dirname "$0")


export MY_CP=$dir/../target/heapSpank-0.3.jar:$dir/heapSpank-0.3.jar


#javap -cp $MY_CP com.github.eostermueller.heapspank.leakyspank.console.LeakySpankConsole
java -cp $MY_CP com.github.eostermueller.heapspank.leakyspank.console.LeakySpankConsole $1
