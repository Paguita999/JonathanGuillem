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
    print('Resultados:');
    for (var result in results) {
      print(result);
    }
  } catch (e) {
    print('Error: $e');
  }
}
