import java.sql.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Farm {

    enum Creature {FARM_ANIMAL,SERVICE_ANIMAL,BIRD,FISH,FRUIT,VEGETABLE,GRAIN}
final String name;

    public Farm(String name, int age, int annualWeightRate, Creature creature) {
        this.name = name;
        this.age = age;
        this.annualWeightRate = annualWeightRate;
        this.creature = creature;
    }
    final int age;
    final int annualWeightRate;
    final Creature creature;
    int weight ()
    {return age * annualWeightRate;}

    private static List<Farm> setCreatures(){
        return Arrays.asList(new Farm("cat",4,1,Creature.SERVICE_ANIMAL),
                new Farm("cat",6,1,Creature.SERVICE_ANIMAL),
                new Farm("dog",4,5,Creature.SERVICE_ANIMAL),
                new Farm("cow",3,75,Creature.FARM_ANIMAL),
                new Farm("cow",4,75,Creature.FARM_ANIMAL),
                new Farm("pig",2,100,Creature.FARM_ANIMAL),
                new Farm("pig",4,100,Creature.FARM_ANIMAL),
                new Farm("hen",1,2,Creature.BIRD),
                new Farm("hen",1,2,Creature.BIRD),
                new Farm("hen",2,2,Creature.BIRD),
                new Farm("carp",5,1,Creature.FISH),
                new Farm("hen",1,2,Creature.BIRD),
                new Farm("peach",10,20,Creature.FRUIT),
                new Farm("cherry",15,5,Creature.FRUIT),
                new Farm("potato",1,400,Creature.VEGETABLE),
                new Farm("cucumber",1,40,Creature.VEGETABLE),
                new Farm("corn",1,50,Creature.GRAIN)
                );
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAnnualWeightRate() {
        return annualWeightRate;
    }

    public Creature getCreature() {
        return creature;
    }

    public static void printCreaturesSortedByAnnualWeightRate(List<Farm> farms)
    {Comparator<Farm> comparator;
    comparator = (farm1, farm2) ->
            (farm2.getAnnualWeightRate()- farm1.getAnnualWeightRate());
        Stream<Farm> sortedStream =farms.stream().sorted(comparator);
        Consumer<Farm> action = (farm) ->{
            System.out.println(farm.getName() + " " + farm.getAnnualWeightRate());
        };
        sortedStream.forEach(action);
    }

    public static void main(String[] args) {
        List<Farm> farms = setCreatures();
        System.out.println("Обитатели нашей фермы по возрасту:");
        farms.stream().sorted(Comparator.comparing(Farm::getAge)).forEach(farm -> System.out.println(farm.getName() + " " + farm.getAge()));
        System.out.println("Кто больше всех на ферме прибавляет в весе по убыванию:");
        Farm.printCreaturesSortedByAnnualWeightRate(farms);
        System.out.println("Кто на нашей ферме весит больше 100 кг:");
        farms.stream().filter(farm -> farm.weight()>100).forEach(farm -> System.out.println(farm.getName() + " " + farm.weight()));
        System.out.println("На ферме есть только сельскохозяйственные животные: " + farms.stream().allMatch(farm -> farm.getCreature().equals(Creature.FARM_ANIMAL)));
        System.out.println("На ферме есть свиньи: " + farms.stream().anyMatch(farm -> farm.getName().equals("pig")));
        System.out.println("На ферме ничего не весит 400 килограммов: " + farms.stream().noneMatch(farm -> farm.weight()==400  ));

        System.out.println("Группировка по сущностям и сортировка результата по возрасту: ");
        Map<Creature,List<Farm>> groupCreaturesByType = farms.stream().collect(Collectors.groupingBy(Farm::getCreature));
        groupCreaturesByType.forEach((creature, farm) ->{
            System.out.println(creature);
            farm.stream().sorted(Comparator.comparing(Farm::getAge)).forEach(farm1 -> System.out.println(farm1.getName() + " " + farm1.getAge() + farm1.weight()));
        });
        farms.stream().max(Comparator.comparing(Farm::getAge)).ifPresent(farm ->System.out.println("Самый большой возраст на ферме у: " + farm.getName() + " " + farm.getAge()));
        farms.stream().min(Comparator.comparing(Farm::weight)).ifPresent(farm ->System.out.println("Самый маленький вес на ферме у: " + farm.getName() + " " + farm.getAge()));

    }
}
