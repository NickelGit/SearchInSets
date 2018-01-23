import java.util.*;

/**
 * Created by Nickolas on 16.01.2018.
 */
public final class Intersection {
    //в случае если множества заданы в виде списка интервалов
    public static double nearestNumber(ArrayList<ArrayList<Interval>> listOfSets, double x){
        //создаем список интервалов
        // и инициализируем его пересечением множеств найденных с помощью метода searchIntersection
        ArrayList<Interval> intersection = searchIntersection(listOfSets);

        //если 'x' находится левее крайней левой границы пересечения
        //то возвращаем наименьшее значение принадлежащее пересечению
        if(x < intersection.get(0).getFirstElement()){
            return  intersection.get(0).getFirstElement();
        }

        //если 'x' находится правее крайней правой границы пересечения
        //то возвращаем наибольшее значение принадлежащее пересечению
        else if(x > intersection.get(intersection.size() - 1).getLastElement()){
            return intersection.get(intersection.size() - 1).getLastElement();
        }
        else{
            //если пересечение состоит из 1 интервала
            //то возвращаем 'x'
            if(intersection.size() == 1){
                return x;
            }
            else {
                //т.к. максимальное количество интервалов из которых будет состоять пересечение
                //неизвестно, то целесообразно воспользоваться алгоритмом бинарного поиска

                //объявляем переменные являющиеся границами зоны поиска
                //и инициализируем их индексами границ пересечения
                //исходя из границ поиска определяем индекс интервала который будем проверять
                int searchStart = 0;
                int searchEnd = intersection.size();
                int position = searchStart + ((searchEnd - searchStart)/2);
                while(true){
                    //объявляем переменную типа interval и инициализируем её интервалом из пересечения
                    // согласно расчитаной позитиции поиска (int position)
                    Interval tmpInterval = intersection.get(position);
                    //если выбранный интервал содержит 'x', то возвращаем 'x'
                    if(tmpInterval.contains(x)){
                        return x;
                    }
                    //если 'x' левее выбранного интервала
                    else if(x < tmpInterval.getFirstElement()){
                        //смещаем правую границу поиска на место текущей поисковой позиции
                        searchEnd = position;

                        //расчитываем новое значение индекса поиска как середину между границами поиска
                        position = searchStart + ((searchEnd - searchStart)/2);

                        //если расстояние между границами поиска равняеется 1,
                        // то переменная локализована, и она находится либо:
                        // между интевалами обозначенными границами поиска
                        //или принадлежит интервалу обозначенному левой границей поиска
                        if(searchEnd - searchStart == 1){
                            //проверяем левый интервал границ поиска на содержание 'x'
                            if(intersection.get(searchStart).contains(x)){
                                return x;
                            }
                            //высчитываем расстояние между 'x'
                            // и границами интервалов между которых расположен 'x'
                            double right = intersection.get(searchEnd).getFirstElement() - x;
                            double left = x - intersection.get(searchStart).getLastElement();
                            //сравниваем расстояния и определяем к какой из границ 'x' ближе
                            if(right < left){
                                return intersection.get(searchEnd).getFirstElement();
                            }
                            else {
                                return intersection.get(searchStart).getLastElement();
                            }

                        }
                        else {
                            continue;
                        }
                    }
                    //если 'x' правее выбранного интервала
                    else if(x > tmpInterval.getLastElement()){
                        //смещаем левую границу поиска на место текущей поисковой позиции
                        searchStart = position;

                        //расчитываем новое значение индекса поиска как середину между границами поиска
                        position = searchStart + ((searchEnd - searchStart)/2);

                        //если расстояние между границами поиска равняеется 1,
                        // то переменная локализована, и она находится либо:
                        // между интевалами обозначенными границами поиска
                        //или принадлежит интервалу обозначенному правой границей поиска
                        if(searchEnd - searchStart == 1){
                            //проверяем правый интервал границ поиска на содержание 'x'
                            if(intersection.get(searchEnd).contains(x)){
                                return x;
                            }

                            //высчитываем расстояние между 'x'
                            // и границами интервалов между которых расположен 'x'
                            double right = intersection.get(searchEnd).getFirstElement() - x;
                            double left = x - intersection.get(searchStart).getLastElement();
                            //сравниваем расстояния и определяем к какой из границ 'x' ближе
                            if(right < left){
                                return intersection.get(searchEnd).getFirstElement();
                            }
                            else {
                                return intersection.get(searchStart).getLastElement();
                            }

                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }
    }

    //метод поиска пересечения множеств
    public static ArrayList<Interval> searchIntersection(ArrayList<ArrayList<Interval>> listOfSets){
        //объявляем списки для хранения интервалов
        //с интервалами данного списком будут последовательно сравниваться
        // интервалы прочих множеств из списка множеств
        ArrayList<Interval> intersection = listOfSets.get(0);
        //данный список предназначен для временного хранения найденных
        // в процессе сравнения интервалов пересечения
        ArrayList<Interval> tmpIntersect = new ArrayList<>();

        //объявляем цикл для последовательного перебора всех сножеств из списка множеств
        for(int i = 1; i < listOfSets.size(); i++){
            //очищаем список временного хранения
            tmpIntersect.clear();

            //последовательно сравниваем интервалы пересечения и очередного множества из списка множеств
            for(Interval inter1 : intersection){
                for(Interval inter2 : listOfSets.get(i)){
                    //находим пересечения данных интервалов
                    Interval tmpInter = Intersection.intervalIntersection(inter1, inter2);
                    //если интервалы пересекаются,
                    // то заносим найденный интервал пересечения во временный список
                    if(tmpInter != null){
                        tmpIntersect.add(tmpInter);
                    }
                }
            }
            //очищаем список интервалов пересечения,
            // и заполняем его найденными в ходе итерации интервалами пересечения
            intersection.clear();
            intersection.addAll(tmpIntersect);

            //затем сравниваем найденное пересечение со следующим множеством из списка множеств
        }
        return intersection;
    }

    //метод поиска пересечения интервалов
    public static Interval intervalIntersection(Interval first, Interval second){
        //объявляем 4 переменных для хранения границ сравниваемых интервалов
        // и инициализируем их границами сравниваемых интервалов
        double first1 = first.getFirstElement();
        double first2 = first.getLastElement();
        double second1 = second.getFirstElement();
        double second2 = second.getLastElement();

        //объявляем и инициализируем переменную для хранения найденного пересечения множеств
        Interval intersection = new Interval();

        //если интервалы не пересекаются то возвращаем null
        if(second1 > first2 || second2 < first1){
            return null;
        }
        //сравниваем расположение границ интервалов
        // и определяем как они смещены относительно друг друга
        //высчитываем внутринние границы образующие пересечение
        //и задаем эти границы интервалу intersection с учетом их включения или не включения в интервал
        else if(second1 > first1){
            intersection.setFirstElement(second1);
            intersection.setIncludeFirst(second.isIncludeFirst());
            if(second2 < first2){
                intersection.setLastElement(second2);
                intersection.setIncludeLast(second.isIncludeLast());
            }
            else if(second2 > first2){
                intersection.setLastElement(first2);
                intersection.setIncludeLast(first.isIncludeLast());
            }
            else if(second2 == first2){
                intersection.setLastElement(second2);
                if(first.isIncludeLast() && second.isIncludeLast()){
                    intersection.setIncludeLast(true);
                }
                else{
                    intersection.setIncludeLast(false);
                }
            }
        }
        else if(second1 < first1){
            intersection.setFirstElement(first1);
            intersection.setIncludeFirst(first.isIncludeFirst());
            if(second2 < first2){
                intersection.setLastElement(second2);
                intersection.setIncludeLast(second.isIncludeLast());
            }
            else if(second2 > first2){
                intersection.setLastElement(first2);
                intersection.setIncludeLast(first.isIncludeLast());
            }
            else if(second2 == first2){
                intersection.setLastElement(second2);
                if(first.isIncludeLast() && second.isIncludeLast()){
                    intersection.setIncludeLast(true);
                }
                else{
                    intersection.setIncludeLast(false);
                }
            }
        }
        //если интервалы равны, то инициализируем интервал пересечение их общими границами
        // с учетом их включения или невключения
        else if(second1 == first1){
            intersection.setFirstElement(second1);
            if(first.isIncludeFirst() && second.isIncludeFirst()){
                intersection.setIncludeFirst(true);
            }
            else{
                intersection.setIncludeLast(false);
            }

            if(second2 < first2){
                intersection.setLastElement(second2);
                intersection.setIncludeLast(second.isIncludeLast());
            }
            else if(second2 > first2){
                intersection.setLastElement(first2);
                intersection.setIncludeLast(first.isIncludeLast());
            }
            else if(second2 == first2){
                intersection.setLastElement(second2);
                if(first.isIncludeLast() && second.isIncludeLast()){
                    intersection.setIncludeLast(true);
                }
                else{
                    intersection.setIncludeLast(false);
                }
            }
        }

        return intersection;
    }






    /*ниже представленно первоначальное решение,
    которое в итоге было признанно не соответствующим представленной задаче,
    т.к. множества представленны непосредственно перечеслением элементов,
    но код оставлен как потенциально полезный ( с учетом некоторых допущений)*/



    //в случае если множества заданы в виде списка Set<Double>
    // (т.е. непосредственного перечисления элементов множества)
    public static double nearestNumber(double x, ArrayList<HashSet<Double>> listOfSets ){

        //Находим пересечение всех множеств с помощью метода searchIntersection
        HashSet<Double> intersection = searchIntersectionSet(listOfSets);

        //Если элемент 'x' принадлежит подмножеству являющемуся пересечением множеств из списка
        //возвращаем значение 'x'
        if(intersection.contains(x)){
            return x;
        }

        //создаем отсортированное по возрастанию множество (TreeSet)
        // и заполняем его элементами принадлежащими пересечению
        TreeSet<Double> sortedIntersection = new TreeSet<>(intersection);

        //если 'x' больше наибольшего элемента принадлежащего множеству
        if(sortedIntersection.last() < x){

            //возвращаем наибольший элемент множества, т.к. он ближе всего к 'x'
            return sortedIntersection.last();
        }
        //если 'x' меньше наименьшего элемента принадлежащего множеству
        else if(sortedIntersection.first() > x){

            //возвращаем наименьший элемент множества, т.к. он ближе всего к 'x'
            return sortedIntersection.first();
        }
        //если 'x' находится в границах наименьшего и наибольшего элементов пересечения но не пренадлежит ему
        else{

            //находим элемент меньше 'x' принадлежащий множеству ( далее 'xl')
            double lower = sortedIntersection.lower(x);

            //находим элемент больше 'x' принадлежащий множеству (далее 'xh')
            double higher = sortedIntersection.higher(x);

            // если расстояние от 'xl' до 'x' меньше, чем расстояние от 'xh' до 'x'
            if((x - lower) < (higher - x))
            {
                //возвращаем значение 'xl' (lower)
                return lower;
            }
            else {
                //иначе возвращаем 'xh' (higher)
                // в случае равенства расстояний так же будет возвращаться больший элемент
                return higher;
            }
        }
    }


    //метод поиска пересечения множеств заданых в виде списка Set<Double>
    public static HashSet<Double> searchIntersectionSet(ArrayList<HashSet<Double>> listOfSets){

        //создаем множество и инициализируем его первым элементом списка множеств
        // Создаем множество в реализации HashSet т.к. он оптимален для поиска и проведения выборки
        // благодаря использованию хэширования элементов
        HashSet<Double> intersectionSubSet = listOfSets.get(0);

        //в цикле последовательно определяем пересечение всех множеств с помощью метода retainAll
        for(int i = 1; i < listOfSets.size(); i++){
            intersectionSubSet.retainAll(listOfSets.get(i));
        }
        //Возвращаем подможество являющееся пересечением
        //System.out.println(new TreeSet(intersectionSubSet));
        return intersectionSubSet;
    }

    //формирование списка отрезков  на основании множества заданного перечислением элементов
    //с автоматическим определением шага между элементами
    public static ArrayList<String> getIntervals(TreeSet<Double> set){
        //создаем список в котором будут храниться интервалы
        ArrayList<String> listOfIntervals = new ArrayList<>();

        //создаем итератор для перебора элементов множества
        Iterator<Double> iter = set.iterator();
        //определяем "шаг" множества (т.е. максимально допустимого расстояния между двумя соседними элементами множества,
        // при котором интервал (отрезок, полуинтервал) будет считаться непрерванным.)
        double step = getSizeOfStep(set);

        double previousElement = set.first();
        //указываем в качестве первого элемента интервала первый элемент множества
        double firstIntervalElement = set.first();

        while(iter.hasNext()){
            double currentElement = iter.next();
            //вычисляем расстояние между текущим и предыдущим элементами
            double distance = currentElement - previousElement;
            //если расстояние между элементами больше установленного шага,
            // или текущий элемент является последним элементом множества
            if (distance > step || currentElement == set.last())
                if(currentElement != set.last()){
                    //если текущий элемент не является последним элементом множества
                    // то последним элементом интервала указываем элемент предшествующий текущему
                    // и добавляем интервал в список
                    listOfIntervals.add("[" + firstIntervalElement + "," + previousElement + "]");
                    //Указываем первым элементом следующего интервала текущий элемент
                    firstIntervalElement = currentElement;
                }
                else {
                    //Если текущий элемент является последним элементом множества
                    // то указываем его в качестве последнего элемента интервала
                    // и добавляем интервал в список
                    listOfIntervals.add("[" + firstIntervalElement + "," + currentElement  + "]");
                }
            //присваиваем предыдущему элементу значение текущего, т.к. мы переходим к следующему элементу
            previousElement = currentElement;
        }
        return listOfIntervals;
    }



    //формирование списка отрезков  на основании множества заданного перечислением элементов
    //с определенным пользователем размером шага между элементами
    public static ArrayList<String> getIntervals(TreeSet<Double> set, double stepSize){
        //создаем список в котором будут храниться интервалы
        ArrayList<String> listOfIntervals = new ArrayList<>();

        //создаем итератор для перебора элементов множества
        Iterator<Double> iter = set.iterator();
        //определяем "шаг" множества (т.е. максимально допустимого расстояния между двумя соседними элементами множества,
        // при котором интервал (отрезок, полуинтервал) будет считаться непрерванным.)
        double step = stepSize;

        double previousElement = set.first();
        //указываем в качестве первого элемента интервала первый элемент множества
        double firstIntervalElement = set.first();

        while(iter.hasNext()){
            double currentElement = iter.next();
            //вычисляем расстояние между текущим и предыдущим элементами
            double distance = currentElement - previousElement;
            //если расстояние между элементами больше установленного шага,
            // или текущий элемент является последним элементом множества
            if (distance > step || currentElement == set.last())
                if(currentElement != set.last()){
                    //если текущий элемент не является последним элементом множества
                    // то последним элементом интервала указываем элемент предшествующий текущему
                    // и добавляем интервал в список
                    listOfIntervals.add("[" + firstIntervalElement + "," + previousElement + "]");
                    //Указываем первым элементом следующего интервала текущий элемент
                    firstIntervalElement = currentElement;
                }
                else {
                    //Если текущий элемент является последним элементом множества
                    // то указываем его в качестве последнего элемента интервала
                    // и добавляем интервал в список
                    listOfIntervals.add("[" + firstIntervalElement + "," + currentElement  + "]");
                }
            //присваиваем предыдущему элементу значение текущего, т.к. мы переходим к следующему элементу
            previousElement = currentElement;
        }
        return listOfIntervals;
    }

    //вычисление "шага" множества (т.е. максимально допустимого расстояния между двумя соседними элементами множества,
    // при котором интервал (отрезок, полуинтервал) будет считаться непрерванным.)
    // В данном случае шаг равен минимальному расстоянию между двумя соседними элементами принадлежащими множеству.
    private static double getSizeOfStep(TreeSet<Double> set){
        //создаем итератор для перебора элементов множества
        Iterator<Double> iter = set.iterator();
        //создаем переменную для хранения "шага" множества
        double step = 0;
        //создаем переменную для хранения элемента предшествующего выбранному
        //и инициализируем её первым элементом множества
        double previousElement = set.first();
        //создаем цикл обхода множества
        while(iter.hasNext()){
            //выбыраем следующий элемент множества
            double currentElement = iter.next();
            //определяем расстояние между предыдущим и текущим элементами
            double distance = currentElement - previousElement;
            //если это расстояние меньше текущего размера шага,
            // или шаг равен 0 (т.к. при первом обходе текущий и предыдущий элемент это одно и то же значение
            //и дистанция между ними равна 0 - это всегда было бы минимальное значение
            // не дающее методу корректно работать)
            if (distance < step || step == 0) {
                //присваиваем значение расстояния шагу,
                // т.к. мы ищем минимальное расстояние между соседними элементами
                step = distance;
            }

            //присваиваем предыдущему элементу значение текущего, т.к. мы переходим к следующему элементу
            previousElement = currentElement;
        }

        //возвращаем "шаг" множества равный мнимальному расстоянию между элементами
        return step;

    }

}
