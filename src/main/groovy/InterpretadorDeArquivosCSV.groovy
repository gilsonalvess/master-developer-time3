import com.opencsv.CSVReader
import groovy.transform.CompileStatic

@CompileStatic
class InterpretadorDeArquivosCSV extends InterpretadorDeArquivoAbstrata {


	@Override
	List<Map<String, String>> obtenhaRegistrosArquivo(String nomeArquivo) {
		List<String> conteudoCsv = processeConteudoArquivo(nomeArquivo)
		String header = conteudoCsv.pop()
		List<String> campos = obtenhaCampos(header)
		List<Map<String, String>> registros = criarRegistros(campos, conteudoCsv)
		return registros
	}

	static List<String> processeConteudoArquivo(String nomeArquivo) {
		File arquivoResource = obtenhaArquivoResource(nomeArquivo)
		arquivoResource.withReader { reader ->
			CSVReader csvReader = new CSVReader(reader)
			List<String[]> csvList = csvReader.readAll()
			return csvList.collect {it[0]}
		}
	}

	static List<String> obtenhaCampos(String s) {
		String campos = s.replaceAll('\\s+', '')
		return campos.split(';') as List<String>
	}

	static List<Map<String, String>> criarRegistros (List<String> campos, List<String> conteudo){
		List<Map<String, String>> registros = []

		for (String linha in conteudo){
			List<String> valores = linha.split(';') as List<String>
			registros << obtenhaItemRegistro(valores, campos)
		}
		registros
	}

	static Map<String, String> obtenhaItemRegistro(List<String> valores, List<String> campos){
		Map<String, String> itemRegistro = [:]
		for (int i = 0; valores.size() > i; i++){
			itemRegistro.putAt(campos[i], valores[i].trim())
		}
		return itemRegistro
	}

}
