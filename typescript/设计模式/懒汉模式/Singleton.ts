class Singleton {
  private static instance: Singleton

  private constructor () {}

  static getInstance (): Singleton {
    if (!Singleton.instance) {
      Singleton.instance = new Singleton()
    }
    return this.instance
  }
}