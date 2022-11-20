public class Word {
    public String field;
    public String value;

    public Word(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getValueAsInt() {
        return Integer.parseInt(value);
    }
    
    public void setValueAsInt(int value) {
        this.value = Integer.toString(value);
    }

    public String toString(){
        return field + ":" + value;
    }
}
