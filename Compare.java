package org.example;
import java.util.Comparator;
public class Compare implements Comparator<Person> {

	public int compare(Person arg0, Person arg1) {
		return arg0.getFirstName().compareTo(arg1.getFirstName());
	}
}
/* 
// public class Compare<Person> implements Comparator<Person> {
// public class Compare<Person> implements Some<Person> {
public class Compared {

	public int compare(Person arg0, Person arg1) {
		// var per = new Person("sf", "la");
		// return arg0.getFirstName().compareTo(arg1.getFirstName());
		// return arg0.compareTo(arg1);
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'compare'");
		return 0;
	}

    public void overr(Person per) {
		per.getFirstName();
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'overr'");
    }

	public static void main(String args){
		var so = new Comparator<Person>(){

            @Override
            public int compare(Person arg0, Person arg1) {
				arg0.getFirstName();
            }
		};
	}
}

interface Some<T> {
	public abstract void overr(T so);
}
*/
