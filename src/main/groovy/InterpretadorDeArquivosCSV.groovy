import com.opencsv.CSVParser
import com.opencsv.CSVReader
import groovy.transform.CompileStatic
import org.hibernate.sql.Alias

@CompileStatic
class InterpretadorDeArquivosCSV extends InterpretadorDeArquivoAbstrata {


	@Override
	Map<GString,List<Map<String,String>>> obtenhaRegistrosArquivo(String nomeArquivo) {
		List<String> conteudoCsv = processeConteudoArquivo(nomeArquivo)
		String header = conteudoCsv.get(0)
		conteudoCsv.remove(0)
		List<String> campos = obtenhaCampos(header)
		List<Map<String, String>> registros = criarRegistros(campos, conteudoCsv)
		return registros.groupBy {final Map<String, String> registro ->
			"${registro.matricula}"
		}
	}

	static List<String> processeConteudoArquivo(String nomeArquivo) {
		File arquivoResource = obtenhaArquivoResource(nomeArquivo)
		String conteudoTexto = arquivoResource.text
		List<String> conteudoList = conteudoTexto.findAll('(.*\\n)')
		conteudoList
	}

	static List<String> obtenhaCampos(String s) {
		String campos = s.replaceAll('\\s+', '')
		return campos.split(',') as List<String>
	}

	static List<Map<String, String>> criarRegistros (List<String> campos, List<String> conteudo){
		List<Map<String, String>> registros = []

		for (String linha in conteudo){
			List<String> valores = linha.tokenize(',') as List<String>
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
