package com.enagorik;

import com.enagorik.api.ApiServer;
import com.enagorik.repository.FileRepository;
import com.enagorik.service.CivicService;
import java.nio.file.Path;

public final class Main {
  public static void main(String[] args) throws Exception {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
    var repository = new FileRepository(Path.of(System.getProperty("enagorik.data", "data")));
    var service = new CivicService(repository);
    service.seed();
    new ApiServer(port, service).start();
    System.out.println("E-Nagorik listening on http://localhost:" + port);
  }
}
