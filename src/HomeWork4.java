import java.util.*;

public class HomeWork4 {

    public static ArrayList<String> surname = new ArrayList<String>();
    public static ArrayList<String> name = new ArrayList<String>();
    public static ArrayList<String> patronymic = new ArrayList<String>();
    public static ArrayList<Boolean> gender = new ArrayList<Boolean>();
    public static ArrayList<Integer> age = new ArrayList<Integer>();
    public static List<Integer> listPerson = new LinkedList<Integer>();

    public static void main(String[] args) {
        Boolean flag = true;    // Флаг желания ввода нового сотрудника

        while (flag) {
            Scanner scanEnter = new Scanner(System.in);
            System.out.print("Вы хотите ввести нового сотрудника? (Д/Н) ");
            String yesOrNo = scanEnter.nextLine();
            if (String.valueOf(yesOrNo.toUpperCase().charAt(0)).equals("Д")) {
                enterDataPerson();
                listPerson.add(age.size() - 1);
            } else if (String.valueOf(yesOrNo.toUpperCase().charAt(0)).equals("Н")) {
                flag = false;
            } else {
                System.out.println("Нужно ввести Д или Н. Попробуйте еще раз!");
            }
        }

        System.out.println("Выберите номер желаемого действия:");
        System.out.println("1 - сортировка по возрасту");
        System.out.println("2 - множественная сортировка: по полу -> по возрасту -> по фамилии (по первой букве)");
        Scanner scanChoise = new Scanner(System.in);
        int choise = scanChoise.nextInt();
        boolean flagChoise = false;
        while (!flagChoise) {
            if (choise == 1) {      //Если выбрали 1 - сортировка по возрасту
                flagChoise = true;
                sortAge(listPerson);
                printData();
            } else if (choise == 2) {   //Если выбрали 2 - множественная сортировка
                flagChoise = true;
                sortGender(); // Отсортировали по полу

                //Сортировка по возрасту
                int sizeList = listPerson.size();
                int indFem = -1;
                for (int i = 0; i < sizeList; i++) {
                    if (gender.get(listPerson.get(i))) {
                        indFem = i;
                        break;
                    }
                }
                if (indFem > 0) {
                    sortAge(listPerson.subList(0, indFem));
                    sortAge(listPerson.subList(indFem, sizeList));
                } else {
                    sortAge(listPerson);
                }
                //Сортировка по фамилии
                int ind1 = 0;
                int ind2 = 0;
                int index = 0;
                if (indFem > 0) {
                    while (index < indFem - 1) {
                        if (age.get(listPerson.get(index)) == age.get(listPerson.get(index + 1))) {
                            ind2 = index + 1;
                        } else {
                            sortSurname(listPerson.subList(ind1, ind2 + 1));
                            ind2 += 1;
                            ind1 = ind2;
                        }
                        index++;
                    }
                    sortSurname(listPerson.subList(ind1, ind2 + 1));
                    index++;
                    ind1 = index;
                    ind2 = index;
                    while (index < sizeList - 1) {
                        if (age.get(listPerson.get(index)) == age.get(listPerson.get(index + 1))) {
                            ind2 = index + 1;
                        } else {
                            sortSurname(listPerson.subList(ind1, ind2 + 1));
                            ind2 += 1;
                            ind1 = ind2;
                        }
                        index++;
                    }
                    sortSurname(listPerson.subList(ind1, ind2 + 1));
                } else {
                    sortSurname(listPerson);
                }
                printData();
            } else {
                System.out.print("Неправильно введенный номер действия! Попробуйте ввести еще раз! ");
                choise = scanChoise.nextInt();
            }
        }
    }

    public static void enterDataPerson() {  //Ввод данных
        Scanner scanName = new Scanner(System.in);
        System.out.print("Введите фамилию, имя, отчество сотрудника через пробел: ");
        String stringName = scanName.nextLine();
        String[] namePerson = stringName.split(" ");
        if (namePerson.length == 3) {
            surname.add(namePerson[0]);
            name.add(namePerson[1]);
            patronymic.add(namePerson[2]);
        } else if (namePerson.length == 2) {
            surname.add(namePerson[0]);
            name.add(namePerson[1]);
            patronymic.add("");
        } else {
            surname.add(namePerson[0]);
            name.add("");
            patronymic.add("");
        }
        Scanner scanGender = new Scanner(System.in);
        System.out.print("Введите пол сотрудника (М или Ж): ");
        String gend = scanGender.nextLine();
        boolean flagGend = false;
        while (!flagGend) {
            if (gend.toUpperCase().equals("М")) {
                gender.add(false);
                flagGend = true;
            } else if (gend.toUpperCase().equals("Ж")) {
                gender.add(true);
                flagGend = true;
            } else {
                System.out.println("Пол введен неправильно. Попробуйте еще раз!");
                System.out.print("Введите пол сотрудника (М или Ж): ");
                gend = scanGender.nextLine();
            }
        }
        Scanner scanAge = new Scanner(System.in);
        System.out.print("Введите возраст сотрудника: ");
        age.add(scanAge.nextInt());
    }

    public static void sortAge(List<Integer> linkList) {    // Сортировка по возрасту
        int index = linkList.size() - 1;
        while (index > -1) {
            int maxAge = age.get(linkList.get(index));
            int indexMax = index;
            for (int i = 0; i < index; i++) {
                if (maxAge < age.get(linkList.get(i))) {
                    maxAge = age.get(linkList.get(i));
                    indexMax = i;
                }
            }
            int temp = linkList.get(index);
            linkList.set(index, linkList.get(indexMax));
            linkList.set(indexMax, temp);
            index--;
        }
    }

    public static void printData() {    //Печать всех списков
        for (int i = 0; i < listPerson.size(); i++) {
            System.out.println("[" + i + "] " + surname.get(listPerson.get(i)) + " " + name.get(listPerson.get(i)) +
                    " " + patronymic.get(listPerson.get(i)) + " " + age.get(listPerson.get(i)) + " " +
                    gender.get(listPerson.get(i)) );
        }
    }

    public static void sortGender() { // Сортировка по полу
        int size = gender.size();
        for (int i = 0; i < size; i++) {
            if (gender.get(listPerson.get(i))) {
                int j = i + 1;
                boolean flag = false;
                while (j < size && !flag) {
                    if (!gender.get(listPerson.get(j))) {
                        int temp = listPerson.get(i);
                        listPerson.set(i, listPerson.get(j));
                        listPerson.set(j, temp);
                        flag = true;
                    }
                    j++;
                }
            }
        }
    }

    public static void sortSurname(List<Integer> linkList) { //Сортировка по фамилии
        int index = linkList.size() - 1;
        while (index > -1) {
            int maxChar = surname.get(linkList.get(index)).toUpperCase().charAt(0);
            int indexMax = index;
            for (int i = 0; i < index; i++) {
                if (maxChar < surname.get(linkList.get(i)).toUpperCase().charAt(0)) {
                    maxChar = surname.get(linkList.get(i)).toUpperCase().charAt(0);
                    indexMax = i;
                }
            }
            int temp = linkList.get(index);
            linkList.set(index, linkList.get(indexMax));
            linkList.set(indexMax, temp);
            index--;
        }
    }
}
