# SistemaDifuso

Implementação de um aplicativo para calculo de lógica difusa

Permite:

* Definir Váriaveis;
* Definir objetivos;
* Definir regras;
* Possui modificadores pré definidos;


# Variáveis e Objetivo

Ao definir variáveis é necessário que todas tenham suas **medidas** e valores de **Núcleo** e **Suporte**.

O Objetivo é definido por uma das variáveis adicionadas.

# Regras

As Regras são definidas da seguinte maneira:

`SE [antecedente] ENTAO [consequente]`

**Antecedente:** possui condições que, quando satisfeitas (mesmo que parcialmente), determinam o processamento do conseqüente através de um mecanismo de inferência difusa.

**Conseqüente:** composto por ações ou diagnósticos que são gerados com o disparo da regra. O consulto de todoas os consequentes das regras criadas são agrupados para o calculo de resolução do aplicativo.

# Modificadores

Os modificados são utilizar para modificar o valor da váriavel. Possuindo a função de advérbio. 'Ex: Aceleração é **muito** lenta'

* **Muito:** Operador de contração, que reduz o valor de pertinência de elementos com baixo valor.

Ex: `f(x) = x²`

* **Mais ou menos:** Operador de ditalação, que beneficia valores de pertinência mais baixos do que os altos.

Ex: `f(x) = 2^(0,5)`

* **De Fato:** Operador de intensificação, beneficiando valores acima de 0.5 e prejudica os demais valores.

Ex: 
`PARA 0,5 >= x <= 0 -->> f(x) = 2(x)²`
`PARA 1 <= x > 0,5 -->> f(x) = 1-2(1-x)²`

* **Muito, Muito:** Operador de potência, similar ao modificado **Muito**, porém tende a ser mais precisa com elementos muito altos. A Aplicação utiliza `p = 3`.

Ex: `f(x) = x^(p)`

# Exemplo incluso.

Ao carregar o arquivo '/save/save.xml' será possivel interegir com o seguinte exemplo:

**Variáveis:**

![](https://github.com/samuelgenio/SistemaDifuso/blob/master/save/aceleracao.png)
![](https://github.com/samuelgenio/SistemaDifuso/blob/master/save/peso.png)
![](https://github.com/samuelgenio/SistemaDifuso/blob/master/save/potencia.png)

**Objetivo:**

![](https://github.com/samuelgenio/SistemaDifuso/blob/master/save/consumo.png)
