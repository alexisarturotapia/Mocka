package com.alexistapia.demomocka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoMockaController {

    private final ConcurrentService concurrentService;

    public DemoController(ConcurrentService concurrentService) {
        this.concurrentService = concurrentService;
    }

    // Stream API con Producto
    @GetMapping("/stream")
    public List<Producto> getStream() {
        List<Producto> productos = Arrays.asList(
                new Producto("Arroz", 20.0),
                new Producto("Azúcar", 15.0),
                new Producto("Aceite", 30.0),
                new Producto("Manzana", 5.0)
        );

        return productos.stream()
                .filter(producto -> producto.getNombre().startsWith("A"))
                .map(producto -> {
                    producto.setPrecio(producto.getPrecio() * 1.1); // Aumentar precio en 10%
                    return producto;
                })
                .collect(Collectors.toList());
    }

    // Concurrencia con hilos y bloqueos
    @GetMapping("/concurrencia")
    public String getConcurrencia() {
        Thread thread1 = new Thread(concurrentService::performTaskWithLock, "Hilo-1");
        Thread thread2 = new Thread(concurrentService::performTaskWithLock, "Hilo-2");
        Thread thread3 = new Thread(concurrentService::performTaskWithoutLock, "Hilo-3");

        thread1.start();
        thread2.start();
        thread3.start();

        return "Tareas concurrentes iniciadas. Revisa la consola para ver los resultados.";
    }

    // WebClient
    @GetMapping("/webclient")
    public String getWebClient(@RequestParam String url) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // RestTemplate
    @GetMapping("/resttemplate")
    public String getRestTemplate(@RequestParam String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
