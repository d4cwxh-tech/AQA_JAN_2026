class Car {
    String color;
    String owner;


    Car(String color, String owner) {
        this.color = color;
        this.owner = owner;
    }

    void info() {
        System.out.println("color: " + color + ", owner: " + owner);
    }
}

 class Main {
    public static void main(String[] args) {


        Car car1 = new Car("white", "walter");
        Car car2 = new Car("black", "mike");

        car1.info();
        car2.info();
    }
}