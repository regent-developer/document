class Singleton {

  private static instance = new Singleton()

  // 将 constructor 设为私有属性，防止 new 调用
  private constructor () {}

  static getInstance (): Singleton {
    return Singleton.instance
  }
}