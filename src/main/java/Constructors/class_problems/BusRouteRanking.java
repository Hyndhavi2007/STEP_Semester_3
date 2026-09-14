package Constructors.class_problems;

public class BusRouteRanking {

    static class BusRoute {
        private String routeCode;
        private String routeName;
        private int priority;

        BusRoute(String routeCode, String routeName, int priority) {
            this.routeCode = routeCode;
            this.routeName = routeName;
            this.priority = priority;
        }

        BusRoute(String routeCode, String routeName) {
            this(routeCode, routeName, 3);
        }

        int compareTo(BusRoute other) {
            if (this.priority != other.priority) {
                return Integer.compare(other.priority, this.priority);
            }

            int codeResult =
                    this.routeCode.compareToIgnoreCase(other.routeCode);

            if (codeResult != 0) {
                return codeResult;
            }

            return this.routeName.compareToIgnoreCase(other.routeName);
        }

        static BusRoute[] rankRoutes(BusRoute[] routes) {
            BusRoute[] result = new BusRoute[routes.length];

            for (int i = 0; i < routes.length; i++) {
                result[i] = routes[i];
            }

            for (int i = 1; i < result.length; i++) {
                BusRoute current = result[i];
                int j = i - 1;

                while (j >= 0 && result[j].compareTo(current) > 0) {
                    result[j + 1] = result[j];
                    j--;
                }

                result[j + 1] = current;
            }

            return result;
        }

        String getRouteCode() {
            return routeCode;
        }
    }

    public static void main(String[] args) {
        BusRoute[] routes = {
                new BusRoute("RT205L", "Airport Express", 3),
                new BusRoute("rt201j", "City Central", 4),
                new BusRoute("RT299T", "Night Service")
        };

        BusRoute[] ranked = BusRoute.rankRoutes(routes);

        for (BusRoute route : ranked) {
            System.out.println(route.getRouteCode());
        }
    }
}