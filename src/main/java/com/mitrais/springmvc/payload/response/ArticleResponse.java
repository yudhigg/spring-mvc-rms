package com.mitrais.springmvc.payload.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public class ArticleResponse extends ApiResponse {

    @SerializedName("data")
    @Expose
    private List<Data> data;

    @SerializedName("form_error")
    @Expose
    private HashMap<String, String> formError;

    public HashMap<String, String> getFormError() {
        return formError;
    }

    public void setFormError(HashMap<String, String> formError) {
        this.formError = formError;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        public Data() {
        }

        @SerializedName("id")
        @Expose
        private Integer id;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("body")
        @Expose
        private String content;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}