import spock.lang.Specification

class InterpretadorDeArquivosCSVSpec extends Specification{

	void 'importa_registros_csv_corretamente'(){
		setup:
		InterpretadorDeArquivosCSV interpretadorDeArquivosCSV = new InterpretadorDeArquivosCSV()
		List<Map<String, String>> registros = interpretadorDeArquivosCSV.obtenhaRegistrosArquivo('pagamento_glosamax.csv')

	}

}
