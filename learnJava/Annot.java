import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

@SomeAnnots(author = "First Last", date = "3/3/2025", currRev = 2, lastMod = "", lastModBy = "", reviewers = {"one", "two" })
public class Annot {

	@SomeAnnots(author = "Meth LasMeth", date = "2/2/2025", reviewers = { "fir", "sec" })
	public void SomeMeth(){
		Method m[] = getClass().getMethods();
		System.out.printf("Val is %s\n", m[1].getName());
		SomeAnnots an = m[1].getAnnotation(SomeAnnots.class);
		System.out.printf("Val is %s\n", an);

		Class cla = getClass();
		Annotation val = cla.getAnnotation(SomeAnnots.class);
		System.out.printf("Val is %s\n", val);
	}

	public static void main(String as[]){
		Annot ann = new Annot();
		ann.SomeMeth();
	}
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@interface SomeAnnots{
	String author();
	String date();
	int currRev() default 1;
	String lastMod() default "N/a";
	String lastModBy() default "N/a";

	String[] reviewers();
}

class SomeAnn {
/* 
    public String SomeMeth() {
		Method m = Class.forName("Annot").getMethod("author");
		Annot a = m.getAnnotation(Annot.class);
		String val = a.author();
		System.out.println();
		return "";
    }
*/
}
