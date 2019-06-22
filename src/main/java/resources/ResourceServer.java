package resources;

public class ResourceServer implements ResourceServerMBean {
    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    public void setTestResource(TestResource newTestResource){
        testResource = newTestResource;
    }

    @Override
    public String getResName() {
        return testResource.getName();
    }

    @Override
    public int getResAge() {
        return testResource.getAge();
    }

}
