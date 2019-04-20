public class NBody{

  public static void main(String[] args){
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String filename = args[2];
    double radius = readRadius(filename);
    StdDraw.setScale(-radius, radius);
    StdDraw.picture(0, 0, "images/starfield.jpg");
    Body[] planets = readBodies(filename);
    for(int i = 0; i < planets.length; i++){
      planets[i].draw();
    }
    StdDraw.enableDoubleBuffering();
    for(double time = 0; time < T; time = time + dt){
      double[] xForces = new double[planets.length];
      double[] yForces = new double[planets.length];
      for(int j = 0; j < planets.length; j++){
        xForces[j] = planets[j].calcNetForceExertedByX(planets);
        yForces[j] = planets[j].calcNetForceExertedByY(planets);
      }
      for(int k = 0; k < planets.length; k++){
        planets[k].update(dt, xForces[k], yForces[k]);
      }
      StdDraw.picture(0, 0, "images/starfield.jpg");
      for(int l = 0; l < planets.length; l++){
          planets[l].draw();
      }
      StdDraw.show();
      StdDraw.pause(10);
    }
    StdOut.printf("%d\n", planets.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < planets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                      planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                      planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
    }
  }

  public static double readRadius(String str){
    In in = new In(str);
    int numplanets = in.readInt();
    double radius = in.readDouble();
    return radius;
  }

  public static Body[] readBodies(String str){
    In in = new In(str);
    int numplanets = in.readInt();
    double radius = in.readDouble();
    Body[] planets = new Body[numplanets];
    int index = 0;
    while(index < numplanets){
      double xP = in.readDouble();
      double yP = in.readDouble();
      double xV = in.readDouble();
      double yV = in.readDouble();
      double m = in.readDouble();
      String img = in.readString();
      planets[index] = new Body(xP, yP, xV, yV, m, img);
      index = index + 1;
    }
    return planets;
  }
}
