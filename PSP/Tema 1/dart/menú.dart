import 'dart:convert';
import 'package:http/http.dart' as http;

Future<Map<String, dynamic>> fetchUrl(String url) async {
  final response = await http.get(Uri.parse(url));
  if (response.statusCode == 200) {
    return jsonDecode(response.body);
  } else {
    throw Exception('Error al obtener datos de $url');
  }
}

void main() async {
  final urls = [
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=1&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=2&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=3&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=4&pageSize=4',
  ];

  try {
    final results = await Future.wait(urls.map(fetchUrl));
    final pizzas = <Map<String, dynamic>>[];

    // Extraer las pizzas de cada página
    for (var result in results) {
      if (result['records'] != null) {
        pizzas.addAll(List<Map<String, dynamic>>.from(result['records']));
      }
    }

    print('ElJust Eat Pizzeria. Menú.');
    print('=' * 59);

    for (var pizza in pizzas) {
      final name = pizza['nom'] ?? '';
      final price = pizza['preu'] ?? '';
      // Ajustar el nombre a 25 caracteres, alineado a la izquierda
      print('${name.padRight(25)}${price.toString().padLeft(6)}€');
    }
  } catch (e) {
    print('Error: $e');
  }
}
