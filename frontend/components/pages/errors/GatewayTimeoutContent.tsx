"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Timer, Hourglass, Network, ArrowLeft, HomeIcon, RotateCcw } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const GatewayTimeoutContent = () => {
    const [timeoutState, setTimeoutState] = useState<number>(0);
    const [countdown, setCountdown] = useState<number>(30);
    const [isWaiting, setIsWaiting] = useState<boolean>(true);

    useEffect(() => {
        console.error("504 Error: Gateway timeout - Servidor não respondeu a tempo");

        const stateInterval = setInterval(() => {
            setTimeoutState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 2200);

        const countdownInterval = setInterval(() => {
            setCountdown((prev) => {
                if (prev <= 1) {
                    setIsWaiting(false);
                    return 30;
                }
                return prev - 1;
            });
        }, 1000);

        const waitingInterval = setInterval(() => {
            setIsWaiting((prev) => !prev);
        }, 5000);

        return () => {
            clearInterval(stateInterval);
            clearInterval(countdownInterval);
            clearInterval(waitingInterval);
        };
    }, []);

    const getTimeoutIcon = () => {
        switch (timeoutState) {
            case 0:
                return <Timer className="h-20 w-20 text-purple-500 animate-pulse" />;
            case 1:
                return <Hourglass className="h-20 w-20 text-orange-500 animate-bounce" />;
            default:
                return <Network className="h-20 w-20 text-red-500 animate-spin" />;
        }
    };

    const generateProgressBar = () => {
        const progress = ((30 - countdown) / 30) * 100;
        return (
            <div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2 mt-2">
                <div
                    className="bg-purple-500 h-2 rounded-full transition-all duration-1000"
                    style={{ width: `${progress}%` }}
                />
            </div>
        );
    };

    return (
        <div className="min-h-full flex flex-col items-center justify-center bg-gradient-to-b from-purple-50 to-indigo-100 dark:from-gray-900 dark:to-purple-900/20 p-4">
            <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-purple-200 dark:border-purple-700">
                <div className="flex flex-col items-center text-center">
                    <div className="mb-4">
                        {getTimeoutIcon()}
                    </div>

                    <h1 className="text-6xl font-bold mb-2 text-purple-600 dark:text-purple-400">504</h1>
                    <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                        Timeout! ⏰💨
                    </h2>

                    <p className="text-gray-600 dark:text-gray-400 mb-6">
                        O servidor está mais lento que tartaruga correndo na melassa!
                        Parece que ele decidiu fazer uma maratona de soneca. 🐌💤
                    </p>

                    <div className="w-full mb-6 p-4 bg-purple-50 dark:bg-purple-900/20 rounded-lg border border-purple-200 dark:border-purple-700">
                        <p className="text-sm text-purple-700 dark:text-purple-300 mb-3">
                            ⏱️ Gateway em slow motion:
                        </p>
                        <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs space-y-2">
                            <div className="flex items-center justify-between">
                                <span>Próxima tentativa em:</span>
                                <span className="text-purple-500 font-bold animate-pulse">
                                    {countdown}s
                                </span>
                            </div>
                            {generateProgressBar()}
                            <div className="flex items-center justify-between">
                                <span>Status da conexão:</span>
                                <span className={isWaiting ? "text-yellow-500" : "text-red-500"}>
                                    {isWaiting ? "🕐 Esperando..." : "💀 Morreu"}
                                </span>
                            </div>
                            <div className="flex items-center justify-between">
                                <span>Velocidade atual:</span>
                                <span className="text-red-500">-1 MB/s 🐌</span>
                            </div>
                            <div className="text-center text-xs text-gray-500 mt-2 border-t pt-2">
                                "Carregando... ainda carregando... ainda carregando..."
                            </div>
                        </div>
                    </div>

                    <div className="space-y-4 w-full">
                        <div className="p-4 bg-orange-50 dark:bg-orange-900/20 rounded-lg">
                            <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                🚀 Dicas para acelerar a vida:
                            </p>
                            <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                <div className="flex items-center">
                                    <span className="mr-2">📡</span>
                                    Cheque sua conexão (pode ser sua internet!)
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">🔄</span>
                                    Tente atualizar a página
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">☕</span>
                                    Faça um café e volte em 10 minutos
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">🙏</span>
                                    Reze para os deuses da internet
                                </div>
                            </div>
                        </div>

                        <div className="flex flex-col sm:flex-row gap-3 justify-center">
                            <Button
                                onClick={() => window.history.back()}
                                variant="outline"
                                className="flex-1"
                            >
                                <ArrowLeft className="h-4 w-4 mr-2" />
                                Desistir
                            </Button>
                            <Button
                                onClick={() => window.location.reload()}
                                variant="secondary"
                                className="flex-1"
                                disabled={isWaiting}
                            >
                                <RotateCcw className="h-4 w-4 mr-2" />
                                {isWaiting ? "Aguarde..." : "Tentar Novamente"}
                            </Button>
                            <Link href="/" className="flex-1">
                                <Button className="w-full bg-purple-500 hover:bg-purple-600">
                                    <HomeIcon className="h-4 w-4 mr-2" />
                                    Escapar
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </Card>

            <div className="mt-8 text-center">
                <p className="text-gray-600 dark:text-gray-400 text-sm">
                    🎭 Plot twist: Talvez o problema seja você! (brincadeira... ou não 😏)
                </p>
            </div>
        </div>
    );
};

export default GatewayTimeoutContent;