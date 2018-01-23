/**
 * Created by Nickolas on 18.01.2018.
 */

/* Класс Interval предназначен для хранения интервалов,полуинтервалов и отрезков.
 *  */
public class Interval {
    private double firstElement;
    private double lastElement;
    private boolean includeFirst;
    private boolean includeLast;

    public Interval(){

    }

    public Interval(double firstElement, boolean includeFirst, double lastElement,  boolean includeLast) {
        this.firstElement = firstElement; //начальная граница интервала
        this.lastElement = lastElement; //конечная граница интервала
        this.includeFirst = includeFirst; //включен ли первый элемент в интервал
        this.includeLast = includeLast; //включен ли последний элемент в интервал
    }

    //метод проверяющий содержит ли интервал внутри себя
    public boolean contains(double x){
        if(x > firstElement && x < lastElement){
            return true;
        }
        else if(x == firstElement){
            if (isIncludeFirst()){
                return true;
            }
            else{
                return false;
            }
        }
        else if(x == lastElement){
            if(isIncludeLast()){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }

    }

    public double getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(double firstElement) {
        this.firstElement = firstElement;
    }

    public double getLastElement() {
        return lastElement;
    }

    public void setLastElement(double lastElement) {
        this.lastElement = lastElement;
    }

    public boolean isIncludeFirst() {
        return includeFirst;
    }

    public void setIncludeFirst(boolean includeFirst) {
        this.includeFirst = includeFirst;
    }

    public boolean isIncludeLast() {
        return includeLast;
    }

    public void setIncludeLast(boolean includeLast) {
        this.includeLast = includeLast;
    }

    //переопределение для корректного вывода интервала в виде: (0.0,10.0]
    // тип скобок определяется включение или не включением значения в интервал
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isIncludeFirst()){
            sb.append("[");
        }
        else{
            sb.append("(");
        }

        sb.append(firstElement).append(",").append(lastElement);

        if(isIncludeLast()){
            sb.append("]");
        }
        else{
            sb.append(")");
        }
        return sb.toString();
    }
}
