package resources;

public class ResourceServer {
    private final TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    public String getResName() {
        return testResource.getName();
    }

    public int getResAge() {
        return testResource.getAge();
    }

}
