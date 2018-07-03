package com.softmasters.dawuro.utils;

import java.util.Date;
import java.util.List;

public class AddressResults {

    List<Results> results;
    String status;
    String error_message;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public class Results {

        List<AddressComponents> address_components;
        String formatted_address;
        Geometry geometry;
        String partial_match;
        String place_id;
        List<String> types;

        public List<AddressComponents> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponents> address_components) {
            this.address_components = address_components;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getPartial_match() {
            return partial_match;
        }

        public void setPartial_match(String partial_match) {
            this.partial_match = partial_match;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public class AddressComponents {

            String long_name;
            String short_name;
            List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;

            }
        }

        public class Geometry {

            Viewport bounds;
            Location location;
            String location_type;
            Viewport viewport;

            public Viewport getBounds() {
                return bounds;
            }

            public void setBounds(Viewport bounds) {
                this.bounds = bounds;
            }

            public Location getLocation() {
                return location;
            }

            public void setLocation(Location location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public Viewport getViewport() {
                return viewport;
            }

            public void setViewport(Viewport viewport) {
                this.viewport = viewport;
            }


            public class Viewport {

                Northeast northeast;
                Southwest southwest;

                public class Northeast {
                    String lat;
                    String lng;

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }

                    public String getLng() {
                        return lng;
                    }

                    public void setLng(String lng) {
                        this.lng = lng;
                    }

                }

                public class Southwest {
                    String lat;
                    String lng;

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }

                    public String getLng() {
                        return lng;
                    }

                    public void setLng(String lng) {
                        this.lng = lng;
                    }
                }

                public Northeast getNortheast() {
                    return northeast;
                }

                public void setNortheast(Northeast northeast) {
                    this.northeast = northeast;
                }

                public Southwest getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(Southwest southwest) {
                    this.southwest = southwest;
                }

            }

            public class Location {

                String lat;
                String lng;
                String attachedmsg;
                Date dateCreated;

                public String getLat() {
                    return lat;
                }

                public void setLat(String lat) {
                    this.lat = lat;
                }

                public String getLng() {
                    return lng;
                }

                public void setLng(String lng) {
                    this.lng = lng;
                }

                public String getAttachedmsg() {
                    return attachedmsg;
                }

                public void setAttachedmsg(String attachedmsg) {
                    this.attachedmsg = attachedmsg;
                }

                public Date getDateCreated() {
                    return dateCreated;
                }

                public void setDateCreated(Date dateCreated) {
                    this.dateCreated = dateCreated;
                }

            }
        }
    }
}
