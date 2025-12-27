import { Client, IMessage, StompSubscription } from '@stomp/stompjs';

interface WebSocketConfig {
  brokerURL: string;
  debug?: (str: string) => void;
  reconnectDelay?: number;
  heartbeatIncoming?: number;
  heartbeatOutgoing?: number;
}

class WebSocketService {
  private client: Client | null = null;
  private static instance: WebSocketService;

  public static getInstance(): WebSocketService {
    if (!WebSocketService.instance) {
      WebSocketService.instance = new WebSocketService();
    }
    return WebSocketService.instance;
  }

  public connect(): void {
    if (!this.client) {
      const config: WebSocketConfig = {
        brokerURL: 'ws://localhost:8080/ws',
        debug: (str: string): void => {
          if (process.env.NODE_ENV === 'development') {
            console.log(`WebSocket: ${str}`);
          }
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      };

      this.client = new Client(config);
      this.client.activate();
    }
  }

  public disconnect(): void {
    if (this.client) {
      this.client.deactivate();
      this.client = null;
    }
  }

  public subscribe(destination: string, callback: (message: IMessage) => void): StompSubscription | null {
    if (this.client) {
      return this.client.subscribe(destination, callback);
    }
    console.error('WebSocket client is not connected');
    return null;
  }

  public sendMessage<T = unknown>(destination: string, body: T): void {
    if (!this.client) {
      console.error('WebSocket client is not connected');
      return;
    }
    
    try {
      this.client.publish({
        destination,
        body: JSON.stringify(body),
        headers: { 'content-type': 'application/json' },
      });
    } catch (error) {
      console.error('Error sending WebSocket message:', error);
    }
  }

  public isConnected(): boolean {
    return this.client?.connected || false;
  }
}

export default WebSocketService.getInstance();
