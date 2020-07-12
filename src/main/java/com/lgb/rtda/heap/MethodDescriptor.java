package com.lgb.rtda.heap;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MethodDescriptor {
    @Getter
    List<String> parameterTypes;
    @Getter
    String returnType;
    private MethodDescriptor(){
        parameterTypes = new LinkedList<>();
    }

    public void addParameterType(String type){
        this.parameterTypes.add(type);
    }

    public static MethodDescriptor parseMethodDescriptor(String descriptor) {
       MethodDescriptorParser parser = new MethodDescriptorParser();
       return parser.parse(descriptor);
    }

    public Class getOrgReturnType(){
        if(Objects.isNull(returnType)){
            return null;
        }
        return getJavaOrgType(returnType);
    }

    public java.lang.Class[] javaOrgParameterTypes(){
        java.lang.Class[] res = new java.lang.Class[parameterTypes.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = getJavaOrgType(parameterTypes.get(i));
        }
        return res;
    }

    private static java.lang.Class getJavaOrgType(String descriptor) {
        switch (descriptor){
            case "B":
                return byte.class;
            case "C":
                return char.class;
            case "D":
                return double.class;
            case "F":
                return float.class;
            case "I":
                return int.class;
            case "J":
                return long.class;
            case "S":
                return short.class;
            case "Z":
                return boolean.class;
            default:
                return parseNoPrimitiveJavaOrgType(descriptor);
        }
    }

    private static java.lang.Class parseNoPrimitiveJavaOrgType(String descriptor){
        if(descriptor.startsWith("L")){
            int length = descriptor.length();
            String typeName = descriptor.substring(1, length - 1).replace("/", ".");
            try {
                return Class.forName(typeName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static class MethodDescriptorParser{
        private String raw;
        private int offset;
        private MethodDescriptor parsed;

        public MethodDescriptor parse(String descriptor) {
            this.raw = descriptor;
            this.parsed = new MethodDescriptor();
            this.startParams();
            this.parseParamTypes();
            this.endParams();
            this.parseReturnType();
            this.finish();
            return this.parsed;
        }

        private void startParams() {
            if (this.readUint8() != '(') {
                causePanic();
            }
        }

        private void endParams() {
            if (this.readUint8() != ')') {
                causePanic();
            }
        }

        private void finish(){
            if (this.offset != this.raw.length()){
                this.causePanic();
            }
        }

        private void causePanic() {
            throw new RuntimeException("BAD descriptor：" + this.raw);
        }

        private byte readUint8() {
            byte[] bytes = this.raw.getBytes();
            byte b = bytes[this.offset];
            this.offset++;
            return b;
        }

        private void unreadUint8() {
            this.offset--;
        }

        public void parseParamTypes() {
            while (true) {
                String type = this.parseFieldType();
                if ("".equals(type)) break;
                this.parsed.addParameterType(type);
            }
        }

        private void parseReturnType() {
            if (this.readUint8() == 'V'){
                this.parsed.returnType = "V";
                return;
            }

            this.unreadUint8();
            String type = this.parseFieldType();
            if (!"".equals(type)){
                this.parsed.returnType = type;
                return;
            }

            this.causePanic();
        }

        private String parseFieldType() {
            switch (this.readUint8()) {
                case 'B':
                    return "B";
                case 'C':
                    return "C";
                case 'D':
                    return "D";
                case 'F':
                    return "F";
                case 'I':
                    return "I";
                case 'J':
                    return "J";
                case 'S':
                    return "S";
                case 'Z':
                    return "Z";
                case 'L':
                    return this.parseObjectType();
                case '[':
                    return this.parseArrayType();
                default:
                    this.unreadUint8();
                    return "";
            }
        }

        private String parseObjectType() {
            String unread = this.raw.substring(this.offset);
            int semicolonIndx = unread.indexOf(";");
            if (semicolonIndx == -1) {
                this.causePanic();
                return "";
            }
            int objStart = this.offset - 1;
            int ojbEnd = this.offset + semicolonIndx + 1;
            this.offset = ojbEnd;
            //descriptor
            return this.raw.substring(objStart, ojbEnd);
        }

        private String parseArrayType() {
            int arrStart = this.offset - 1;
            this.parseFieldType();
            int arrEnd = this.offset;
            //descriptor
            return this.raw.substring(arrStart, arrEnd);
        }

    }


}
